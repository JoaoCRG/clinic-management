<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pacientes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>Pacientes</h1>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Telefone</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($pacients as $pacient)
                    <tr>
                        <th scope="row">{{ $pacient['id'] }}</th>
                        <td>{{ $pacient['name'] }}</td>
                        <td>{{ $pacient['phoneNumber'] }}</td>
                        <td><a href="/pacient/{{ $pacients['id'] }}" class="btn btn-primary">Ver mais</a></td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    </div>
</body>
</html>
