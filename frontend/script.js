const apiBaseUrl = "http://localhost:8080/api/appointments";
const backendOfflineMessage = "No hay conexión con el backend. Verifica que Spring Boot esté ejecutándose en el puerto 8080.";

const stateDisplayMap = {
  REGISTERED: "Registrado",
  WAITING: "En espera",
  IN_SERVICE: "En atención",
  COMPLETED: "Finalizado",
  CANCELLED: "Cancelado"
};

const stateBadgeClassMap = {
  REGISTERED: "badge badge--registered",
  WAITING: "badge badge--waiting",
  IN_SERVICE: "badge badge--progress",
  COMPLETED: "badge badge--done",
  CANCELLED: "badge badge--canceled"
};

function getElement(id) {
  return document.getElementById(id);
}

function updateApiStatus(isConnected) {
  const apiStatus = getElement("apiStatus");
  if (!apiStatus) {
    return;
  }

  apiStatus.classList.remove("api-status--connected", "api-status--disconnected");
  if (isConnected) {
    apiStatus.textContent = "Backend conectado";
    apiStatus.classList.add("api-status--connected");
  } else {
    apiStatus.textContent = "Backend no disponible";
    apiStatus.classList.add("api-status--disconnected");
  }
}

function showMessage(message, type) {
  const messageBox = getElement("messageBox");
  if (!messageBox) {
    return;
  }

  const messageItem = document.createElement("div");
  messageItem.className = `message-item message-item--${type}`;

  const messageIcon = document.createElement("span");
  messageIcon.className = "message-icon";
  if (type === "success") {
    messageIcon.textContent = "✔";
  } else if (type === "error") {
    messageIcon.textContent = "✖";
  } else {
    messageIcon.textContent = "i";
  }

  const messageText = document.createElement("span");
  messageText.textContent = message;

  messageItem.appendChild(messageIcon);
  messageItem.appendChild(messageText);
  messageBox.prepend(messageItem);

  if (messageBox.children.length > 6) {
    messageBox.removeChild(messageBox.lastChild);
  }
}

function clearTableBody() {
  const tableBody = getElement("appointmentsTableBody");
  if (tableBody) {
    tableBody.innerHTML = "";
  }
}

function renderEmptyRow(message) {
  const tableBody = getElement("appointmentsTableBody");
  if (!tableBody) {
    return;
  }

  const row = document.createElement("tr");
  const cell = document.createElement("td");
  cell.colSpan = 7;
  cell.className = "table-message";
  cell.textContent = message;
  row.appendChild(cell);
  tableBody.appendChild(row);
}

function formatDate(dateText) {
  if (!dateText) {
    return "-";
  }

  const date = new Date(dateText);
  if (Number.isNaN(date.getTime())) {
    return dateText;
  }

  return date.toLocaleString("es-CO");
}

function getStateDisplay(state) {
  return stateDisplayMap[state] || state;
}

function getStateBadgeClass(state) {
  return stateBadgeClassMap[state] || "badge badge--registered";
}

function createActionButton(label, cssClass, onClick) {
  const button = document.createElement("button");
  button.type = "button";
  button.className = `btn btn-sm ${cssClass}`;
  button.textContent = label;
  button.addEventListener("click", onClick);
  return button;
}

function getActionsForState(appointment, onAction) {
  const actions = [];

  if (appointment.currentState === "REGISTERED") {
    actions.push(createActionButton("Pasar a espera", "btn-info", () => onAction(appointment.id, "waiting")));
    actions.push(createActionButton("Cancelar", "btn-danger", () => onAction(appointment.id, "cancel")));
  } else if (appointment.currentState === "WAITING") {
    actions.push(createActionButton("Iniciar atención", "btn-warning", () => onAction(appointment.id, "start")));
    actions.push(createActionButton("Cancelar", "btn-danger", () => onAction(appointment.id, "cancel")));
  } else if (appointment.currentState === "IN_SERVICE") {
    actions.push(createActionButton("Finalizar turno", "btn-success", () => onAction(appointment.id, "complete")));
  }

  return actions;
}

function renderAppointments(appointments) {
  clearTableBody();

  const tableBody = getElement("appointmentsTableBody");
  if (!tableBody) {
    return;
  }

  if (!appointments || appointments.length === 0) {
    renderEmptyRow("No hay turnos registrados.");
    return;
  }

  appointments.forEach((appointment) => {
    const row = document.createElement("tr");

    const stateLabel = getStateDisplay(appointment.currentState);
    const stateClass = getStateBadgeClass(appointment.currentState);
    const actions = getActionsForState(appointment, updateAppointmentState);

    row.innerHTML = `
      <td class="row-id">${appointment.id}</td>
      <td>${appointment.customerName}</td>
      <td>${appointment.barberName}</td>
      <td>${appointment.serviceName}</td>
      <td><span class="${stateClass}">${stateLabel}</span></td>
      <td class="date-cell">${formatDate(appointment.creationDate)}</td>
      <td class="actions-cell"></td>
    `;

    const actionsCell = row.querySelector(".actions-cell");
    if (actions.length > 0) {
      actions.forEach((button) => actionsCell.appendChild(button));
    } else {
      const noActions = document.createElement("span");
      noActions.className = "no-actions";
      noActions.textContent = "Sin acciones";
      actionsCell.appendChild(noActions);
    }

    tableBody.appendChild(row);
  });
}

async function parseApiError(response) {
  try {
    const errorBody = await response.json();
    if (errorBody && errorBody.message) {
      return errorBody.message;
    }
  } catch (error) {
    return "Error inesperado al procesar la respuesta del servidor.";
  }

  return "Ocurrió un error al procesar la solicitud.";
}

async function loadAppointments() {
  try {
    const response = await fetch(apiBaseUrl);
    if (!response.ok) {
      const errorMessage = await parseApiError(response);
      updateApiStatus(false);
      showMessage(errorMessage, "error");
      return;
    }

    const appointments = await response.json();
    renderAppointments(appointments);
    updateApiStatus(true);
  } catch (error) {
    updateApiStatus(false);
    renderEmptyRow("No se pudo cargar la lista de turnos.");
    showMessage(backendOfflineMessage, "error");
  }
}

async function createAppointment(event) {
  event.preventDefault();

  const customerNameInput = getElement("customerName");
  const barberNameInput = getElement("barberName");
  const serviceNameInput = getElement("serviceName");

  const payload = {
    customerName: customerNameInput.value.trim(),
    barberName: barberNameInput.value.trim(),
    serviceName: serviceNameInput.value.trim()
  };

  if (!payload.customerName || !payload.barberName || !payload.serviceName) {
    showMessage("Todos los campos son obligatorios.", "error");
    return;
  }

  try {
    const response = await fetch(apiBaseUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      const errorMessage = await parseApiError(response);
      showMessage(errorMessage, "error");
      updateApiStatus(true);
      return;
    }

    showMessage("Turno creado correctamente.", "success");
    customerNameInput.value = "";
    barberNameInput.value = "";
    serviceNameInput.value = "";
    await loadAppointments();
  } catch (error) {
    updateApiStatus(false);
    showMessage(backendOfflineMessage, "error");
  }
}

async function updateAppointmentState(id, action) {
  try {
    const response = await fetch(`${apiBaseUrl}/${id}/${action}`, {
      method: "PUT"
    });

    if (!response.ok) {
      const errorMessage = await parseApiError(response);
      showMessage(errorMessage, "error");
      updateApiStatus(true);
      return;
    }

    showMessage("Estado del turno actualizado correctamente.", "success");
    await loadAppointments();
  } catch (error) {
    updateApiStatus(false);
    showMessage(backendOfflineMessage, "error");
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const appointmentForm = getElement("appointmentForm");
  if (appointmentForm) {
    appointmentForm.addEventListener("submit", createAppointment);
  }

  loadAppointments();
});