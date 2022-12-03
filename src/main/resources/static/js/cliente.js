window.onload = async () => {
	const form = document.querySelector("form");
	form.addEventListener("submit", function (e) {
		submitForm(e, this);
	});
};

async function submitForm(e, form) {
	e.preventDefault();

	const headers = {
		"Content-Type": "application/json",
	};

    const jsonFormData = buildJsonFormData(form);

    location.href = `../acompanharPedido/index.html?id=${jsonFormData.id}`;
}

function buildJsonFormData(form) {
	const jsonFormData = {};
	for (const pair of new FormData(form)) {
		jsonFormData[pair[0]] = pair[1];
	}
	return jsonFormData;
}
