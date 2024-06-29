# Birthgap
This program makes a simplified attempt to simulate the change of human population accross time with respect to the next parameters:

  * Initial humanity size = 100
  * Birth rate = 1.0
  * Age of becoming a parent = 30
  * Age to die = 100


The simulation is simplified and does not take into consideration people dying of non-age related reasons.
As well as existence of multiple generations at the beginning of time.
Instead, we start only with a generation of newborns.

Inspired after watching the 'Birthgap' documentary by Steven J Shaw: https://www.youtube.com/watch?v=A6s8QlIGanA

## Output

```
Humanity: population = 100, current year = 2040
  Generation-0: size = 100, age = 10

Humanity: population = 150, current year = 2070
  Generation-1: size = 50, age = 10
  Generation-0: size = 100, age = 40

Humanity: population = 175, current year = 2100
  Generation-2: size = 25, age = 10
  Generation-1: size = 50, age = 40
  Generation-0: size = 100, age = 70

Humanity: population = 187, current year = 2130
  Generation-3: size = 12, age = 10
  Generation-2: size = 25, age = 40
  Generation-1: size = 50, age = 70
  Generation-0: size = 100, age = 100

Humanity: population = 93, current year = 2160
  Generation-4: size = 6, age = 10
  Generation-3: size = 12, age = 40
  Generation-2: size = 25, age = 70
  Generation-1: size = 50, age = 100

Humanity: population = 46, current year = 2190
  Generation-5: size = 3, age = 10
  Generation-4: size = 6, age = 40
  Generation-3: size = 12, age = 70
  Generation-2: size = 25, age = 100

Humanity: population = 22, current year = 2220
  Generation-6: size = 1, age = 10
  Generation-5: size = 3, age = 40
  Generation-4: size = 6, age = 70
  Generation-3: size = 12, age = 100

Humanity: population = 10, current year = 2250
  Generation-7: size = 0, age = 10
  Generation-6: size = 1, age = 40
  Generation-5: size = 3, age = 70
  Generation-4: size = 6, age = 100

Humanity: population = 4, current year = 2280
  Generation-8: size = 0, age = 10
  Generation-7: size = 0, age = 40
  Generation-6: size = 1, age = 70
  Generation-5: size = 3, age = 100

Humanity: population = 1, current year = 2310
  Generation-9: size = 0, age = 10
  Generation-8: size = 0, age = 40
  Generation-7: size = 0, age = 70
  Generation-6: size = 1, age = 100

Population collapse, no people left on the planet.
```
