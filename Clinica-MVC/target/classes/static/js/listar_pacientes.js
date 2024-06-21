const tableBody = document.querySelector("#pacientesTable tbody");
function fetchPacientes() {
  // listando los pacientes

  fetch(`/paciente`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((paciente, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
                <td>${paciente.id}</td>
                <td>${paciente.nombre}</td>
                <td>${paciente.apellido}</td>
                <td>${paciente.dni}</td>
                <td>${paciente.fechaIngreso}</td>
                <td>${paciente.domicilio.id}</td>
                <td>${paciente.domicilio.calle}</td>
                <td>${paciente.domicilio.numero}</td>
                <td>${paciente.domicilio.localidad}</td>
                <td>${paciente.domicilio.provincia}</td>
                <td>
                  <button class="btn btn-primary btn-sm" onclick="editPaciente(${paciente.id}, '${paciente.nombre}', '${paciente.apellido}', '${paciente.dni}')">Modificar</button>
                  <button class="btn btn-danger btn-sm" onclick="deletePaciente(${paciente.id})">Eliminar</button>
                </td>
              `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });

  // modificar un paciente

  // eliminar un paciente
}

fetchPacientes();
