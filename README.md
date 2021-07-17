# Nyx Programming Language

![Nyx Icon](https://github.com/danielkleebinder/nyxlang/blob/main/nyxlang.png?raw=true)

The nyx programming language (also known as nyxlang) is a statically and strongly typed, lexically scoped and interpreted programming language that allows the developer
to implement traits and custom types. It is object oriented, but does not support inheritance since inheritance is a common source of
maintainability issues.


(influenced by TypeScript, Kotlin, Go, Eiffel, ML)

## Grammar

The formal language definition in Backus-Naur form looks like the following. Feel free to implement it yourself.

```
<program> ::= <statement-list>

<statement-list> ::= <statement>
                   | <statement> ';' <statement-list>

<statement> ::= ('var' | 'const') <var-declare>
              | <fun-declare>
              | <var-assign>
              | <expr>

<fun-declare> ::= 'fun' <name> ('(' <var-declare> ')')?
                   (':' '(' <expr> ')')?
                   (':' '(' <expr> ')')?
                   ('->' <type>)?
                   (('{' <statement-list> '}') | ('=' <statement>)))

<var-declare> ::= (',' <name> (':' <type>)? ('=' <expr>)? )*
<var-assign>  ::= <name> '=' <expr>

<conditional-expr> ::= 'if' <expr>         '{' <statement-list> '}'
                       ('else' 'if' <expr> '{' <statement-list> '}')*
                       ('else'             '{' <statement-list> '}')?

<loop-expr>   ::= 'loop' (<statement>)? (':' <expr>)? (':' <statement>)? '{' <statement-list> '}'
<native-expr> ::= 'native' '{' <any> '}'

<expr>       ::= <equal-expr> (( '&&' | '||' ) <equal-expr>)*
<equal-expr> ::= <logical-expr> (( '==' | '!=' ) <logical-expr>)*
<arith-expr> ::= <term> (( '+' | '-' ) <term>)*
<logic-expr> ::= NOT <logical-expr>
               | <arith-expr> (( '<' | '<=' | '>' | '>=' ) <arith-expr>)*

<term> ::= <atom> (( '*' | '/' | '%' ) <atom>)*
<atom> ::= ('+' | '-' | '~' | '++' | '--')? (<number> | <var>)
         | '(' <expr> ')'
         | <conditional-expr>
         | <loop-expr>
         | <native-expr>
         | <empty>

<type>  ::= <number> | <bool>
<var>   ::= <name>
<name>  ::= IDENTIFIER
<empty> ::= ()
```

## Turing Completeness
Turing completeness can be proven by showing that a programming language is capable of defining and running µ-recursive
functions. µ-recursive functions are precisely the subset of functions that can be computed by Turing machines. The
primitive µ-recursive functions are:

- **Constant function**. A function that takes `k` parameters and returns a constant value `n` (`C(x1, ..., xk) = n`).
- **Successor function**. A function that returns the successor of a given value (`S(x) = x + 1`)
- **Projection function**. A function that returns a specific value of the specified ones (`P(i, x1, ..., xk) = xi`)

It is left to write those three µ-recursive functions in this programming language to prove Turing completeness and
therefore interchangeability with every other Turing machine.

#### Constant Function
A reduction of the constant function is the zero function which always returns `0` instead of `n`. This can be shown
as follows:

```
fun c(arr: number[]) -> number = 0;
```

#### Successor Function
The successor function returns the successor value of the given parameter. Since numbers are sufficient for µ-recursive
functions, simply increasing the value completes the task.

```
fun s(x: number) -> number = (x + 1);
```

#### Projection Function
The projection function requires that the value of `i` is in the range of the arrays size. This can be assured by using
the `requires` function property of the programming language.

```
fun p(i: number, arr: number[]) : (0 <= i && i < arr.size) -> number = arr[i];
```

Therefore, the proof is done and Turing completeness has been shown.


## Example Program

```kotlin
package MyProgram;

import "system.io";
import "math";


trait Feedable {
  fun feed();
}

trait CanTalk {
  fun talk(text: string);
}

type Dog : Feedable, CanTalk {

  fed: bool, happy: bool;
  age: number;

  fun feed() = fed = true
  fun name() -> string = "Bello"
  fun talk(text: string) = print(name . " says: " . text)

}


// The programming language also supports pre- and postconditions
// (also known as variant and covariant). The application will throw
// an error if one of those is not fullfilled.
//
// The first statement after the parameter list and after the ':' symbol
// is the precondition that the parameters have to fulfill. The statement
// after that is the postcondition. The '_' represents the functions result.
//
fun add(a: number, b: number) : (a > 0 && b > 0) : (_ >= (a + b)) -> number = a + b
fun sub(a: number, b: number) :: (_ <= (a - b)) -> number = a - b

// Simple function without pre- or postcondition
fun printHelloWorld() {
  // Runs the code block in the interpreters native language
  native {
    System.out.println("Hello from Java");
  }
}

// The "main" function is the entry point into the program
fun main() {

  // Constants must be initialized on declaration and cannot
  // be modified at a later stage.
  const pi: number = 3.141592654;
  const square = fun (x: number) -> number = x * x

  // var dog = Dog;
  // var dog = Dog { fed: false };
  var dog = Dog { false }

  // There is only the "loop" in this programming language. No
  // for, while or do-whiles. You can do everything with this.
  loop var i = 0 :: i++ {
    if dog.fed {
      break
    }
    dog.feed()
  }

  loop !dog.fed {
    dog.feed()
  }

  // When is similar to switch-case in other languages
  when dog.fed {
    true {
      dog.happy = true;
      print("Yummy, I am full!");
    }
    false { print("I want some more food") }
    else { print("This is not possible") }
  }

  print(dog.fed)       // true
  print(dog == dog)    // true
  print(~(-7+3**2))    // -3

  // Starts the given block in a coroutine
  run { dog.feed() }

  printHelloWorld()
}
```