<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Paciente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>{{ $pacient['name'] }}</h1>
        <p><strong>Telefone:</strong> {{ $pacient['phone_number'] }}</p>
        <p><strong>Registro Médico:</strong> {{ $pacient['medical_registry'] ?? 'Não disponível' }}</p>
        <a href="/pacients" class="btn btn-secondary">Voltar</a>
    </div>
</body>
</html>
