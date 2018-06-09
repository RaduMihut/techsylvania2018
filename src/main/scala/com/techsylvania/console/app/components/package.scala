package com.techsylvania.console.app

package object components {
  case class BasicData(id: Int, timestamp: String, air: Int)
  case class Basic(id: Int, timestamp: String, air: Int)

  object Basic{
    def apply(bData: BasicData): Basic = Basic(bData.id, bData.timestamp, bData.air)
  }
}
