document.querySelector('#nome').addEventListener('keyup', event => {
    const nameContains = event.target.value;
    const tecnologiaId = document.getElementById("tecnologiaId").value;
    findProductsByNomeAndTecnologia(stringToBase64(nameContains), tecnologiaId);
});

document.querySelector('#tecnologiaId').addEventListener('change', event => {
    const nameContains = document.getElementById("nome").value;
    const tecnologiaId = event.target.value;
    findProductsByNomeAndTecnologia(stringToBase64(nameContains), tecnologiaId);
});

async function findProductsByNomeAndTecnologia(nameContains, tecnologiaId) {
let starters = await fetch('http://localhost:8080/admin/starter/json?nome='+ nameContains +'&tecnologiaId=' + tecnologiaId )
    .then(data => data.json());
    console.log(starters);

let count = Object.keys(starters).length;

document.querySelector('#tableBody').innerHTML = '';

if(count <= 0 ){
    document.querySelector('#tableBody').innerHTML = 'Nenhum starter encontrado.';
} else {
    document.querySelector('#tableBody').innerHTML = starters.map(starter => `
    <tr>
        <td>${starter.id}</td>
        <td>${starter.nome}</td>
        <td>${starter.sobrenome}</td>
        <td>${starter.quatroLetras}</td>
        <td>${starter.grupo.tecnologia.nome}</td>
        <td><div class="btn-group mb-3">
            <a class=" btn btn-outline-primary btn-sm text-decoration-none" href="http://localhost:8080/admin/starter/info?id=${starter.id}" role="button">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-info-lg" viewBox="0 0 16 16">
                    <path d="m9.708 6.075-3.024.379-.108.502.595.108c.387.093.464.232.38.619l-.975 4.577c-.255 1.183.14 1.74 1.067 1.74.72 0 1.554-.332 1.933-.789l.116-.549c-.263.232-.65.325-.905.325-.363 0-.494-.255-.402-.704l1.323-6.208Zm.091-2.755a1.32 1.32 0 1 1-2.64 0 1.32 1.32 0 0 1 2.64 0Z"/>
                </svg></a>
            <a class=" btn btn-outline-dark btn-sm text-decoration-none" href="http://localhost:8080/admin/starter/editar?id=${starter.id}" role="button">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                    <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                </svg></a>
            <a class="btn btn-outline-danger btn-sm text-decoration-none" href="http://localhost:8080/admin/starter/deletar?id=${starter.id}" onclick="return confirm('ADVERTENCIA:\n\nExcluir esse Starter irá permanentemente apagar todos os dailies e projetos relacionados a ele.\n\nDeseja continuar?')" role="button">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
            </svg></a>
        </div></td>
    </tr>
`).join('');
}
}

function stringToBase64(string) {
return btoa(unescape(encodeURIComponent(string)));
}