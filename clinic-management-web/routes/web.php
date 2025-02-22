<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\PacientController;

Route::get('/pacients/all', [PacientController::class, 'index']);
Route::get('/pacients/{id}', [PacientController::class, 'show']);




Route::get('/', function () {
    return view('welcome');
});
