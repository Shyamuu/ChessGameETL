// etl-common/src/main/scala/org/example/model/ChessGame.scala
package org.example.model

case class ChessGame(
                      id: String,
                      rated: Boolean,
                      created_at: String,
                      last_move_at: String,
                      turns: Int,
                      victory_status: String,
                      winner: String,
                      increment_code: String,
                      white_id: String,
                      white_rating: Int,
                      black_id: String,
                      black_rating: Int,
                      moves: String,
                      opening_eco: String,
                      opening_name: String,
                      opening_ply: Int
                    )
