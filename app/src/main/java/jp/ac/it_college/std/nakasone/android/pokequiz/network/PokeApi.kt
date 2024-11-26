package jp.ac.it_college.std.nakasone.android.pokequiz.network

object PokeApi {
    val service: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}