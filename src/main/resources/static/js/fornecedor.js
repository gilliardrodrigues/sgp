window.onload = async () => {
	const fornecedores = await getFornecedores();
	const content = document.getElementsByClassName("content")[0];
	fornecedores.forEach(fornecedor => {
		const fornecedorHTML = montarHTMLFornecedor(fornecedor);
		content.append(fornecedorHTML);
	}, []);
};

async function getFornecedores() {
	const fornecedores = await fetch("http://localhost:8080/fornecedores").then(response => response.json());

	return fornecedores;
}

function montarHTMLFornecedor(fornecedor) {
	let tabela = document.createElement("div");
	tabela.className = "tabela";

	Object.keys(fornecedor).forEach((key, index) => {
		const col = document.createElement("div");
		col.classList.add("column");
		col.classList.add("w12_5");
		if (index == 0) col.classList.add("first-column");

		const p = document.createElement("p");

		p.textContent = key === "id" ? "#" + fornecedor[key] : fornecedor[key];

		col.appendChild(p);
		tabela.appendChild(col);
	});

	tabela.innerHTML += `<div class="column ver-obs w12"> <a href="../verObsFornecedor/index.html?id=${fornecedor.id}"> <button type="submit" style="border: 0; background: transparent"> <p>Ver<br>observações</p></button> </a> </div><div class="column edit-fornecedor w8"> <a href="../editarFornecedor/index.html?id=${fornecedor.id}"> <button type="submit" class="edit-button" style="border: 0; background: transparent"> <img src="../../static/img/edit-button.svg" width="20px" alt="submit"/> </button> </a> </div><div class="column delete-button w5 last-column"> <button onclick="removerFornecedor(${fornecedor.id})"style="border: 0; background: transparent"> <img src="../../static/img/trash-icon.png" width="20px" alt="submit"/> </button></div>`
	
	return tabela;
}

async function removerFornecedor(id) {
	await fetch(`http://localhost:8080/fornecedores/${id}`, {
		method: "DELETE",
	});
	location.reload();
}