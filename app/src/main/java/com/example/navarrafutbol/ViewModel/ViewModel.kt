package com.example.navarrafutbol.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navarrafutbol.model.Categoria
import com.example.navarrafutbol.response.CategoriaResponse
import com.example.navarrafutbol.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultadosViewModel : ViewModel() {
    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarDatos() {
        _error.value = null
        val api = RetrofitClient.instance

        val primeraCall = api.getPartidosByCategoria(1)
        val preferenteCall = api.getPartidosByCategoria(2)

        primeraCall.enqueue(object : Callback<CategoriaResponse> {
            override fun onResponse(call: Call<CategoriaResponse>, response: Response<CategoriaResponse>) {
                val primera = response.body()
                if (!response.isSuccessful) {
                    _error.value = "Primera: código ${response.code()}"
                }

                preferenteCall.enqueue(object : Callback<CategoriaResponse> {
                    override fun onResponse(call: Call<CategoriaResponse>, response: Response<CategoriaResponse>) {
                        val preferente = response.body()
                        if (!response.isSuccessful) {
                            _error.value = "Preferente: código ${response.code()}"
                        }

                        if (primera != null && preferente != null) {
                            _categorias.value = listOf(primera.toCategoria(), preferente.toCategoria())
                        } else {
                            _error.value = "Error cargando alguna categoría"
                        }
                    }

                    override fun onFailure(call: Call<CategoriaResponse>, t: Throwable) {
                        _error.value = "Error Preferente: ${t.message}"
                    }
                })
            }

            override fun onFailure(call: Call<CategoriaResponse>, t: Throwable) {
                _error.value = "Error Primera Autonómica: ${t.message}"
            }
        })
    }

    private fun CategoriaResponse.toCategoria(): Categoria {
        return Categoria(
            id = 0,
            categoria = this.categoria,
            gruposWrapper = this.grupos
        )
    }
}
