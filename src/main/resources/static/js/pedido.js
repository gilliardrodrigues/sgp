function loadPedidos() {
	console.log(getPedidos());
}

window.onload = async () => {
	const pedidos = await getPedidos();
	const content = document.getElementsByClassName("content")[0];
	pedidos.forEach(pedido => {
		const pedidoHTML = montarHTMLPedido(pedido);
		content.append(pedidoHTML);
		console.log(pedidoHTML);
	}, []);

};

async function getPedidos() {
	const pedidos = await fetch("http://localhost:8080/pedidos").then(response => response.json());

	return pedidos;
}

function montarHTMLPedidos(pedido) {
	let tabela = document.createElement("div");
	tabela.className = "tabela";

	Object.keys(pedido).forEach((key, index) => {
		const col = document.createElement("div");
		col.classList.add("column");
		col.classList.add("w10");
		if (index == 0) col.classList.add("first-column");

		const p = document.createElement("p");

		p.textContent = key === "id" ? "#" + pedido[key] : pedido[key];

		col.appendChild(p);
		tabela.appendChild(col);
	});

	tabela.innerHTML += `<div class="column w7 last-column"> <a href="../editarPedido/index.html?id=${pedido.id}"> <button type="submit" class="edit-button" style="border: 0; background: transparent"> <img src="../../static/img/edit-button.svg" width="20px" alt="submit"/> </div>`
	
	return tabela;
}

async function removerPedido(id) {
	await fetch(`http://localhost:8080/pedidos/${id}`, {
		method: "DELETE",
	});
	location.reload();
}

async function submitForm(e, form) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

	const jsonFormData = buildJsonFormData(form);

	await fetch(`http://localhost:8080/pedidos/${id}`, {
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