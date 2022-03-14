document.querySelector('#tecnologiaId').addEventListener('change', event => {
    const nameContains = '';
    const tecnologiaId = event.target.value;
    findProductsByNomeAndTecnologia(stringToBase64(nameContains), tecnologiaId);
});

async function findProductsByNomeAndTecnologia(nameContains, tecnologiaId) {
let starters = await fetch('http://localhost:8080/admin/starter/json/topStarters?nome='+ nameContains +'&tecnologiaId=' + tecnologiaId )
    .then(data => data.json());
    console.log(starters);

let count = Object.keys(starters).length;

document.querySelector('#tableBody').innerHTML = '';

if(count <= 0 ){
    document.querySelector('#tableBody').innerHTML = 'Nenhum starter encontrado.';
} else {
    document.querySelector('#tableBody').innerHTML = starters.map(starter => `
    <tr>
        <td>${starter.nome}</td>
        <td>${starter.sobrenome}</td>
        <td>${starter.quatroLetras}</td>
        <td>${starter.grupo.tecnologia.nome}</td>
        <td>${starter.nota}</td>
        <td><div class="btn-group mb-3">
            <a class=" btn btn-outline-primary btn-sm text-decoration-none" href="http://localhost:8080/admin/starter/info?id=${starter.id}" role="button">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-info-lg" viewBox="0 0 16 16">
                    <path d="m9.708 6.075-3.024.379-.108.502.595.108c.387.093.464.232.38.619l-.975 4.577c-.255 1.183.14 1.74 1.067 1.74.72 0 1.554-.332 1.933-.789l.116-.549c-.263.232-.65.325-.905.325-.363 0-.494-.255-.402-.704l1.323-6.208Zm.091-2.755a1.32 1.32 0 1 1-2.64 0 1.32 1.32 0 0 1 2.64 0Z"/>
                </svg></a>
        </div></td>
    </tr>
`).join('');
}
}

function stringToBase64(string) {
return btoa(unescape(encodeURIComponent(string)));
}