//> using scala 3.4.1
//> using options -Wnonunit-statement, -Wunused:all, -Wvalue-discard, -Yno-experimental, -Ysafe-init, -deprecation, -feature, -new-syntax, -unchecked,

import scala.util.chaining.*

final case class Generation(size: Int, number: Int, age: Int):

  override def toString(): String =
    s"Generation-$number: size = $size, age = $age"

final case class Humanity(generations: List[Generation], year: Int):
  val size = generations.map(_.size).sum
  val genNum = generations.size

  override def toString(): String =
    s"""|Humanity: population = $size, current year = $year
        |${generations.reverse.map(g => "  " + g.toString()).mkString("\n")}
        |""".stripMargin

val initialHumanitySize = 100
val birthRate = 1.0
val parentsAge = 30
val dieAge = 100

println(
  s"""|This program makes a simplified attempt to simulate the change of human population accross time with respect to the next parameters:
      |
      |  * Initial humanity size = $initialHumanitySize
      |  * Birth rate = $birthRate
      |  * Age of becoming a parent = $parentsAge
      |  * Age to die = $dieAge
      |
      |
      |The simulation is simplified and does not take into consideration people dying of non-age related reasons.
      |As well as existence of multiple generations at the beginning of time.
      |Instead, we start only with a generation of newborns.
      |
      |Inspired after watching the 'Birthgap' documentary by Steven J Shaw: https://www.youtube.com/watch?v=A6s8QlIGanA
      |""".stripMargin
)

val initial = Humanity(
  generations =
    List(Generation(size = initialHumanitySize, number = 0, age = 0)),
  year = 2030,
)

def newGeneration(generation: Generation): Generation =
  val femalesNum = generation.size / 2
  val newbornsNum = (femalesNum * birthRate).toInt
  Generation(size = newbornsNum, number = generation.number + 1, age = 0)

def yearTick(humanity: Humanity): Humanity = humanity.copy(
  generations = humanity.generations.map(g => g.copy(age = g.age + 1)),
  year = humanity.year + 1,
)

def selectFutureParents(humanity: Humanity): Option[Generation] = humanity
  .generations
  .find(g => g.age == parentsAge)

def selectOldGenToDie(humanity: Humanity): Option[Generation] = humanity
  .generations
  .find(g => g.age == dieAge)

@annotation.tailrec
def loop(humanity: Humanity): Unit =
  if humanity.size == 0 then
    println("Population collapse, no people left on the planet.")
  else
    if humanity.year % parentsAge == 0 then println(humanity)

    val withNewborns = selectFutureParents(humanity)
      .fold(humanity): futureParentsGen =>
        val newbornsGen = newGeneration(futureParentsGen)
        humanity.copy(generations = humanity.generations.appended(newbornsGen))

    val withoutDead = selectOldGenToDie(humanity)
      .fold(withNewborns): oldPeople =>
        humanity.copy(generations =
          humanity.generations.filterNot(g => g.number == oldPeople.number)
        )
    val afterYear = yearTick(withoutDead)

    loop(afterYear)

loop(initial)
