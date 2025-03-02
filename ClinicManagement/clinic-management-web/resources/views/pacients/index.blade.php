<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pacientes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form method="GET" action="{{ route('pacients.index') }}" class="mb-4">
    <div class="row">
        <div class="col-md-8">
            <input type="text" name="search" class="form-control" placeholder="Buscar por nome" value="{{ $search }}">
        </div>
        <div class="col-md-4">
            <button type="submit" class="btn btn-primary w-100">Pesquisar</button>
        </div>
    </div>
</form>

<!-- Exibindo a tabela de pacientes -->
<table class="table">
    <thead>
        <tr>
            <th>#</th>
            <th>Nome</th>
            <th>Detalhes</th>
        </tr>
    </thead>
    <tbody>
        @foreach ($pacients as $pacient)
            <tr>
                <td>{{ $pacient['id'] }}</td>
                <td>{{ $pacient['name'] }}</td>
                <td>
                    <a href="{{ route('pacients.show', $pacient['id']) }}" class="btn btn-info">Detalhes</a>
                </td>
            </tr>
        @endforeach
    </tbody>
</table>

<!-- Navegação da página -->
<div class="d-flex justify-content-center">
    <ul class="pagination">
        @for ($i = 1; $i <= $totalPages; $i++)
            <li class="page-item {{ $i == $currentPage ? 'active' : '' }}">
                <a class="page-link" href="{{ route('pacients.index', ['search' => $search, 'page' => $i]) }}">{{ $i }}</a>
            </li>
        @endfor
    </ul>
</div>

    <div class="container mt-4">
        <h1>Pacientes</h1>

        <!-- Campo de pesquisa -->
        <form method="GET" action="{{ route('pacients.index') }}" class="mb-4">
            <div class="row">
                <div class="col-md-8">
                    <input type="text" name="search" class="form-control" placeholder="Buscar por nome" value="{{ request()->search }}">
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Pesquisar</button>
                </div>
            </div>
        </form>

        <!-- Botão para adicionar paciente -->
        <a href="{{ route('pacients.create') }}" class="btn btn-success mb-3">Adicionar Paciente</a>

        <!-- Tabela de Pacientes -->
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Detalhes</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($pacients as $pacient)
                    <tr>
                        <th scope="row">{{ $pacient['id'] }}</th>
                        <td>{{ $pacient['name'] }}</td>
                        <td>
                            <!-- Botão para abrir a Modal com abas -->
                            <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#pacientModal" onclick="openModal({{ json_encode($pacient) }})">
                                Detalhes
                            </button>
                        </td>
                    </tr>
                @endforeach
            </tbody>
        </table>

        <!-- Paginação -->
        <div class="d-flex justify-content-center">
            {{ $pacients->links() }}
        </div>
    </div>

    <!-- Modal para Detalhes do Paciente -->
    <div class="modal fade" id="pacientModal" tabindex="-1" aria-labelledby="pacientModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="pacientModalLabel">Detalhes do Paciente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Abas -->
                    <ul class="nav nav-tabs" id="pacientTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <a class="nav-link active" id="medicalRegistryTab" data-bs-toggle="tab" href="#medicalRegistry" role="tab" aria-controls="medicalRegistry" aria-selected="true">Registro Médico</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" id="appointmentHistoryTab" data-bs-toggle="tab" href="#appointmentHistory" role="tab" aria-controls="appointmentHistory" aria-selected="false">Histórico de Consultas</a>
                        </li>
                    </ul>

                    <div class="tab-content mt-3" id="pacientTabContent">
                        <!-- Aba de Registro Médico -->
                        <div class="tab-pane fade show active" id="medicalRegistry" role="tabpanel" aria-labelledby="medicalRegistryTab">
                            <h5>Registro Médico</h5>
                            <p><strong>Última Visita:</strong> <span id="lastDoctorVisit">N/A</span></p>
                            <p><strong>Hospitalização:</strong> <span id="hospitalization">N/A</span></p>
                            <p><strong>Motivo da Hospitalização:</strong> <span id="hospitalizationReason">N/A</span></p>
                            <p><strong>Medicação:</strong> <span id="medication">N/A</span></p>
                            <p><strong>Doenças:</strong> <span id="diseases">N/A</span></p>
                        </div>

                        <!-- Aba de Histórico de Consultas -->
                        <div class="tab-pane fade" id="appointmentHistory" role="tabpanel" aria-labelledby="appointmentHistoryTab">
                            <h5>Consultas Passadas</h5>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Data</th>
                                        <th>Procedimento</th>
                                        <th>Lotes</th>
                                    </tr>
                                </thead>
                                <tbody id="appointmentHistoryBody">
                                    <!-- Aqui será inserido o histórico de consultas -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Script para carregar os dados na Modal -->
    <script>
        function openModal(pacient) {
            // Preenche os dados do registro médico
            document.getElementById('lastDoctorVisit').textContent = pacient.medicalRegistry?.lastDoctorVisit ?? 'N/A';
            document.getElementById('hospitalization').textContent = pacient.medicalRegistry?.hospitalization ? 'Sim' : 'Não';
            document.getElementById('hospitalizationReason').textContent = pacient.medicalRegistry?.hospitalizationReason ?? 'N/A';
            document.getElementById('medication').textContent = pacient.medicalRegistry?.medication ?? 'N/A';
            document.getElementById('diseases').textContent = pacient.medicalRegistry?.diseases ?? 'N/A';

            // Preenche o histórico de consultas
            let appointmentHistoryBody = document.getElementById('appointmentHistoryBody');
            appointmentHistoryBody.innerHTML = ''; // Limpa o conteúdo anterior
            if (pacient.appointmentHistory && pacient.appointmentHistory.length > 0) {
                pacient.appointmentHistory.forEach(function(appointment) {
                    let row = document.createElement('tr');
                    row.innerHTML = `<td>${appointment.date}</td><td>${appointment.procedure}</td>`;
                    appointmentHistoryBody.appendChild(row);
                });
            } else {
                let row = document.createElement('tr');
                row.innerHTML = '<td colspan="2">Nenhum registro encontrado.</td>';
                appointmentHistoryBody.appendChild(row);
            }
        }
    </script>
</body>
</html>
