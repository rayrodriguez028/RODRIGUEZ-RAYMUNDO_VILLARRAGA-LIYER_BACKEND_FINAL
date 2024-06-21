// listando los odontologos
function buscarOdontologos() {
const tableBody = document.querySelector("#odontologosTable tbody");
  fetch(`/odontologo`)
  .then((response) => {
    if (!response.ok) {
        console.log(response);
        throw new Error(`HTTP error! Status: ${response.status} ${response.url}`);
    }
    return response.json();
  })
  .then((data) => {
        // Limpiar el contenido actual de la tabla
        tableBody.innerHTML = "";
        // Insertar los datos en la tabla
        data.forEach((odontologo, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${odontologo.id}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.nroMatricula}</td>
                <td>
                   <button type="button" class="btn btn-primary btn-sm" onclick="modificarOdontologo(${odontologo.id}, '${odontologo.nombre}', '${odontologo.apellido}', '${odontologo.nroMatricula}')">Modificar</button>
                   <button type="button" class="btn btn-danger btn-sm" onclick="eliminarOdontologo(${odontologo.id})">Eliminar</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
   });
}
// eliminar un odontologo
function eliminarOdontologo(id) {
    //confirmamos eliminación de odontologo
    if(confirm(`¿Desea eliminar el odontologo con id: ${id}?`)) {
        fetch(`/odontologo/${id}`,{
            method: "DELETE",
            headers: {"Content-Type": "application/json"}
            })
        .then((response) => {
            if (!response.ok) {
                console.log(response);
                throw new Error(`HTTP error! Status: ${response.status} ${response.url}`);
            }
            return response.json();
          })
          .then((data) => {
              console.log(data);
              console.log(data.message);
              alert(data.message);
              limpiarForm();
              buscarOdontologos();
            });
    }
}

// modificar un odontologo
function modificarOdontologo(id, nombre, apellido, matricula) {
      document.getElementById("id").value = id;
      document.getElementById("nombre").value = nombre;
      document.getElementById("apellido").value = apellido;
      document.getElementById("matricula").value = matricula;
}

// agrega odontologo
function guardarOdontolgo() {
const id = document.getElementById("id").value;
const val = id ==="";
const mensaje = val ? "agregar" : "modificar";

if(confirm(`¿Desea ${mensaje} el odontologo?`)) {
      const form = document.getElementById("agregarForm");
      const id = document.getElementById("id").value;
      const nombre = document.getElementById("nombre").value;
      const apellido = document.getElementById("apellido").value;
      const matricula = document.getElementById("matricula").value;

      // llamando al endpoint de agregar o modificar
        fetch(`/odontologo`, {
            method: val ? "POST" : "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({id, nombre, apellido, nroMatricula: matricula })
        })
        .then((response) => {
            if (!response.ok) {
                console.log(response);
                throw new Error(`HTTP error! Status: ${response.status} ${response.url}`);
            }
            return response.json();
          })
          .then((data) => {
              const salida = val ? "odontologo agregado" : "odontologo modificado" ;
              console.log(data);
              console.log(salida)
              alert(salida);
              limpiarForm();
              buscarOdontologos();
          });
    }
}

function limpiarForm() {
    document.getElementById("agregarForm").reset();
}

buscarOdontologos();