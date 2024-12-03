package jp.ac.it_college.std.nakasone.android.pokequiz.network

/**
 * [PokeApiService] のインスタンスを保持するだけのオブジェクト
 */
object PokeApi {
    /**
     * [lazy] を使用して、初めてアクセスがあったときに [retrofit] に
     * [PokeApiService] のインスタンスを生成してもらう
     */
    val service: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}