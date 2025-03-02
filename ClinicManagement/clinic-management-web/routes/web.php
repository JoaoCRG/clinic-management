<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\PacientController;

Route::get('/pacients', [PacientController::class, 'index'])->name('pacients.index');
Route::get('/pacients/{id}', [PacientController::class, 'show'])->name('pacient.details');
Route::put('/pacient/{id}/updateMedicalRegistry', [PacientController::class, 'updateMedicalRegistry'])->name('pacient.updateMedicalRegistry');
Route::get('/pacients/create', [PacientController::class, 'create'])->name('pacients.create');
Route::post('/pacients', [PacientController::class, 'store'])->name('pacients.store');





Route::get('/', function () {
    return view('welcome');
});
