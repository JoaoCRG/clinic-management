<?php
namespace App\Http\Controllers;

use Illuminate\Support\Facades\Http;
use Illuminate\Http\Request;

class PacientController extends Controller
{
    public function index()
    {
        // Fazer a requisição para a API
        $response = Http::withBasicAuth('user', '12345')
                        ->get(env('API_URL') . '/pacients/all');
        
        // Verificar se a resposta foi bem-sucedida
        if ($response->successful()) {
            $pacients = $response->json();  // Resposta JSON da API
            
            // Verifique se é um array antes de passar para a view
            if (is_array($pacients) || is_object($pacients)) {
                return view('pacients.index', ['pacients' => $pacients]);
            } else {
                return response()->json(['error' => 'Dados inválidos retornados pela API'], 500);
            }
        } else {
            return response()->json(['error' => 'Erro ao acessar API'], 500);
        }
    }
    
    
}
