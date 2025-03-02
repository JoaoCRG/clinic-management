<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Http;
use Illuminate\Http\Request;

class PacientController extends Controller
{
    public function index(Request $request)
    {
        // Captura o termo de pesquisa (se houver)
        $search = $request->input('search');
        
        // Fazer a requisição para a API com base na pesquisa
        $response = Http::withBasicAuth('user', '12345')
                        ->get(env('API_URL') . '/pacients/all', [
                            'search' => $search // Envia o parâmetro de busca para a API
                        ]);
        
        // Verificar se a resposta foi bem-sucedida
        if ($response->successful()) {
            $pacients = $response->json();  // Resposta JSON da API
            
            // Verifique se é um array antes de passar para a view
            if (is_array($pacients) || is_object($pacients)) {
                // Simulando a paginação no frontend
                $perPage = 10;
                $currentPage = $request->input('page', 1); // Pega a página atual ou 1
                $offset = ($currentPage - 1) * $perPage;
                $paginatedPacients = array_slice($pacients, $offset, $perPage);
                
                // Paginação manual
                $totalPacients = count($pacients);
                $totalPages = ceil($totalPacients / $perPage);
                
                return view('pacients.index', [
                    'pacients' => $paginatedPacients,
                    'search' => $search,
                    'currentPage' => $currentPage,
                    'totalPages' => $totalPages,
                ]);
            } else {
                return response()->json(['error' => 'Dados inválidos retornados pela API'], 500);
            }
        } else {
            return response()->json(['error' => 'Erro ao acessar API'], 500);
        }
    }

    
    public function show($id)
    {
        // Fazer a requisição para a API com o ID do paciente
        $response = Http::withBasicAuth('user', '12345')
                        ->get(env('API_URL') . '/pacients/' . $id);
        
        // Verificar se a resposta foi bem-sucedida
        if ($response->successful()) {
            $pacient = $response->json();  // Resposta JSON do paciente
            
            // Verificar se o paciente foi encontrado e retornar a view
            if ($pacient) {
                return view('pacients.details', compact('pacient'));
            } else {
                return response()->json(['error' => 'Paciente não encontrado'], 404);
            }
        } else {
            return response()->json(['error' => 'Erro ao acessar API'], 500);
        }
    }
    

    public function updateMedicalRegistry(Request $request, $id)
    {
        $pacient = Pacient::findOrFail($id);

        // Validação
        $validated = $request->validate([
            'lastDoctorVisit' => 'nullable|date',
            'hospitalization' => 'required|boolean',
            'hospitalizationReason' => 'nullable|string',
            'medication' => 'nullable|string',
            'diseases' => 'nullable|string',
        ]);

        // Atualizando os dados do Registro Médico
        $pacient->medicalRegistry->update([
            'lastDoctorVisit' => $validated['lastDoctorVisit'],
            'hospitalization' => $validated['hospitalization'],
            'hospitalizationReason' => $validated['hospitalizationReason'],
            'medication' => $validated['medication'],
            'diseases' => $validated['diseases'],
        ]);

        return redirect()->route('pacients.details', $id)->with('success', 'Registro médico atualizado com sucesso.');
    }
    public function create()
{
    return view('pacients.create'); // Formulário de criação de paciente
}

public function store(Request $request)
{
    $validated = $request->validate([
        'name' => 'required|string|max:255',
        'email' => 'required|email',
        // outros campos para o paciente
    ]);

    // Fazendo a requisição para adicionar um paciente
    $response = Http::withBasicAuth('user', '12345')
                    ->post(env('API_URL') . '/pacients', $validated);
    
    if ($response->successful()) {
        return redirect()->route('pacients.index')->with('success', 'Paciente adicionado com sucesso!');
    } else {
        return redirect()->back()->with('error', 'Erro ao adicionar paciente.');
    }
}

}
