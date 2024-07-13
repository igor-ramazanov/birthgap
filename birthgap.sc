//> using scala 3.4.2
//> using options -Wnonunit-statement, -Wunused:all, -Wvalue-discard, -Yno-experimental, -Ysafe-init, -deprecation, -feature, -new-syntax, -unchecked,

import scala.util.chaining.*

object Params:
  val initialHumanitySize = 100
  val birthRate = 1.0
  val parentsAge = 30
  val dieAge = 100

final case class Generation(size: Int, index: Int, age: Int):

  override def toString(): String =
    s"Generation-$index: size = $size, age = $age"

  def giveBirth(): Generation =
    val femalesNum = size / 2
    val newbornsNum = (femalesNum * Params.birthRate).toInt
    Generation(size = newbornsNum, index = index + 1, age = 0)

final case class Humanity(generations: List[Generation], year: Int):
  val size = generations.map(_.size).sum
  val genNum = generations.size

  override def toString(): String =
    s"""|Humanity: population = $size, current year = $year
        |${generations.reverse.map(g => "  " + g.toString()).mkString("\n")}
        |""".stripMargin

  val isCollapsed: Boolean = size == 0

  def tickYear(): Humanity = nextParents()
    .fold(this): parents =>
      add(parents.giveBirth())
    .pipe: humanity =>
      humanity
        .elders()
        .fold(humanity): elders =>
          humanity.remove(elders.index)
    .tickYearHelper()

  private def nextParents(): Option[Generation] = generations
    .find(g => g.age == Params.parentsAge)

  private def add(generation: Generation): Humanity =
    copy(generations = generations.appended(generation))

  private def elders(): Option[Generation] = generations
    .find(g => g.age == Params.dieAge)

  private def remove(genIndex: Int): Humanity =
    copy(generations = generations.filterNot(g => g.index == genIndex))

  private def tickYearHelper(): Humanity = copy(
    generations = generations.map(g => g.copy(age = g.age + 1)),
    year = year + 1,
  )

object Humanity:
  val initial = Humanity(
    generations =
      List(Generation(size = Params.initialHumanitySize, index = 0, age = 0)),
    year = 2030,
  )

println(
  s"""|This program makes a simplified attempt to simulate the change of human population accross time with respect to the next parameters:
      |
      |  * Initial humanity size = ${Params.initialHumanitySize}
      |  * Birth rate = ${Params.birthRate}
      |  * Age of becoming a parent = ${Params.parentsAge}
      |  * Age to die = ${Params.dieAge}
      |
      |
      |The simulation is simplified and does not take into consideration people dying of non-age related reasons.
      |As well as existence of multiple generations at the beginning of time.
      |Instead, we start only with a generation of newborns.
      |
      |Inspired after watching the 'Birthgap' documentary by Steven J Shaw: https://www.youtube.com/watch?v=A6s8QlIGanA
      |""".stripMargin
)

@annotation.tailrec
def loop(humanity: Humanity): Unit =
  if humanity.isCollapsed then
    println("Population collapse, no people left on the planet.")
  else
    if humanity.year % Params.parentsAge == 0 then println(humanity)
    loop(humanity.tickYear())

loop(Humanity.initial)
