window.onload = async () => {
	const temporadas = await getTemporadas();
	const content = document.getElementsByClassName("content")[0];
	temporadas.forEach(temporada => {
		const temporadaHTML = montarHTMLTemporada(temporada);
		content.append(temporadaHTML);
		console.log(temporadaHTML);
	}, []);
};

async function getTemporadas() {
	const temporadas = await fetch("http://localhost:8080/temporadas").then(response => response.json());

	return temporadas;
}

function montarHTMLTemporada(temporada) {
	let tabela = document.createElement("div");
	tabela.className = "tabela";

	Object.keys(temporada).forEach((key, index) => {
		const col = document.createElement("div");
		col.classList.add("column");
		col.classList.add("w15");
		if (index == 0) col.classList.add("first-column");

		const p = document.createElement("p");

		p.textContent = key === "id" ? "#" + temporada[key] : temporada[key];

		if (key === "dataInicio" || key === "dataFim") {
			p.textContent = temporada[key] ? new Date(temporada[key]).toLocaleDateString() : "-";
		}

		if (key === "catalogo") {
			p.textContent = Object.keys(temporada[key]);
		}

		col.appendChild(p);
		tabela.appendChild(col);
	});

	tabela.innerHTML += `<div class="column ver-obs w12"> <a> <button onclick="encerrarTemporada()" style="border: 0; background: transparent"> <p>Finalizar <br>temporada </p></button> </a></div><div class="column edit-temporada w8 last-column"> <a href="../editarTemporada/index.html?id=${temporada.id}"> <button type="submit" class="edit-button" style="border: 0; background: transparent"> <img src="../../static/img/edit-button.svg" width="20px" alt="submit"/> </button> </a></div>`

	return tabela;
}

async function removerTemporada(id) {
	await fetch(`http://localhost:8080/temporadas/${id}`, {
		method: "DELETE",
	});
	location.reload();
}

async function encerrarTemporada() {
	await fetch(`http://localhost:8080/temporadas/encerrar`, {
		method: "PUT",
	});
	location.reload();
}

async function submitForm(e, form) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	await fetch(`http://localhost:8080/temporadas/${id}`, {
		method: "DELETE",
		headers,
		body: JSON.stringify(jsonFormData),
	}).then(response => {
		if (!response.ok) {
			response.json().then(body => alert(body.titulo));
		} else {
			location.href = "homeAdmin/index.html";
		}
	});
}
