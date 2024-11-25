# Android II 成績判定の課題  
講義で作成途中だったアプリの完成を目指してください。

## 講義中に作成した内容との違い  
- リポジトリの実装クラス(*RepositoryImpl)のパッケージが移動しました。
  - data.impl → data.repository.impl 
- PokeAPI のデータをパースする Json の設定を変更しました。
  - 結果として SnakeCase なキー名を CamelCase なプロパティ名へ自動変換されます。 
  - (例) official_artwork → officialArtwork 
- プレビュー関数用にモックオブジェクトを作成しています。
  - ~.mock.GenerationsRepositoryMock
  - ~.mock.PokeApiServiceMock
- 世代とポケモンを紐づけるエンティティ名が変更されました。
  - AppearanceMap → PokemonIntroducedGenerationCrossRef
- ResultScreen へ渡すパラメータを変更しています。
  - 正解数 → 世代ID,正解数

## やること
- パッケージ名 jp.ac.it_college.std.s23000.android.pokequiz の s23000 を自分の学籍番号に変更してください。
  - 変える場所
    - 実際のパッケージ名
    - build.gradle.kts (:app)
      - namespace
      - applicationId
- HomeScreen を完成させてください。
  - 画面中央にタイトルとスタートボタンを縦に並べて配置してください。
  - 可能な限り大きな文字サイズで設置してください。
- GenerationScreen を完成させてください。
  - 各世代と全ての世代から出題の合計10個のボタンが必要です。
  - 各世代のボタンを全て手書きはNGです。
  - 正しく選択したボタンに応じて QuizScreen へ移動してください。
  - すべての世代を選択した場合の世代IDは「0」としてください。
- QuizScreen を完成させてください。
  - リポジトリから GenerationWithPokemon を取ってくる
  - リポジトリを通じてデータが登録済みか確認
    - データが無ければ・・・
      - PokeAPI:generation に含まれる pokemon_species をもとにポケモンIDを探し出す
      - リポジトリを通じて世代IDとポケモンIDの組み合わせを登録する
  - GenerationWithPokemon に含まれるポケモンリストを使って問題を10問出題してください。
    - 仕様書通り黒塗りで表示すること
    - 回答後は黒塗りを解除すること
- ResultScreen を完成させてください。
  - 正解数を表示する
  - 同じ世代でリトライするボタンの設置
  - 世代を選び直すボタンの設置

## 出来ると加点
- ナビゲーションスタックを適切に制御して、「戻る」操作で意図しない画面へ戻らないようにする。
- PokeAPI:generation の pokemon_species は、その世代で初登場のポケモンのみなので
  PokeAPI:pokedex を使って図鑑ベースで出題する機能の実装。