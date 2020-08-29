package br.com.cubos.challenge.android.moviescatalog.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}