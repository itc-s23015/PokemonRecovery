package jp.ac.it_college.std.s23000.android.pokequiz.network

object PokeApi {
    val service: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}