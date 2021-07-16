# Nyx Programming Language

![Nyx Icon](https://github.com/danielkleebinder/nyxlang/blob/main/nyxlang.png?raw=true)

(influenced by TypeScript, Kotlin, Go, Eiffel, ML)

## Grammar

The formal language definition looks like the following. Feel free to implement it yourself.

```
<program> ::= <statement-list>

<statement-list> ::= <statement>
                   | <statement> ';' <statement-list>

<statement> ::= ('var' | 'const') <var-declare>
              | <var-assign>
              | <fun-declare>
              | <expr>

<fun-declare> ::= ('entry')? 'fun' <name> '(' <var-declare> ')'
                              ('requires' '(' <expr> ')')?
                              ('ensures' '(' <expr> ')')?
                             '{' <statement-list> '}'

<var-declare> ::= (',' <name> (':' <type>)? ('=' <expr>)? )*
<var-assign> ::= <name> '=' <expr>

<type> ::= <number> | <bool>

<conditional-expr> ::= 'if' <expr>         '{' <statement-list> '}'
                       ('else' 'if' <expr> '{' <statement-list> '}')*
                       ('else'             '{' <statement-list> '}')?

<loop-expr> ::= 'loop' (<expr>)? '{' <statement-list> '}'

<expr>       ::= <equal-expr> (( AND | OR ) <equal-expr>)*
<equal-expr> ::= <logical-expr> (( EQ | NEQ ) <logical-expr>)*
<arith-expr> ::= <term> (( PLUS | MINUS ) <term>)*
<logic-expr> ::= NOT <logical-expr>
               | <arith-expr> (( LT | LTE | GT | GTE ) <arith-expr>)*

<term> ::= <atom> (( MULT | DIVIDE | MOD ) <atom>)*
<atom> ::= PLUS <number>
         | MINUS <number>
         | COMPLEMENT <number>
         | LPAREN <expr> RPAREN
         | <var>
         | <conditional-expr>
         | <loop-expr>

<var> ::= <name>
<name> ::= IDENTIFIER
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
fun c(arr: number[]): number = 0;
```

#### Successor Function
The successor function returns the successor value of the given parameter. Since numbers are sufficient for µ-recursive
functions, simply increasing the value completes the task.

```
fun s(x: number): number = (x + 1);
```

#### Projection Function
The projection function requires that the value of `i` is in the range of the arrays size. This can be assured by using
the `requires` function property of the programming language.

```
fun p(i: number, arr: number[]) requires (0 <= i && i < arr.size): number = arr[i];
```

Therefore, the proof is done and Turing completeness has been shown.


## Interpreter Workflow

```
Program Source Code
         |
         V
       Lexer
         |
         V
       Parser
         |
         V
       Linker
         |
         V
      Analyzer
         |
         V
     Interpreter
```


## Example Program

```
#pragma precision 12

package MyProgram;

import "system.io";
import "math";


trait Feedable {
  fun feed();
}

trait CanTalk {
  fun talk(text: string);
}

class Dog is Feedable, CanTalk {
  fed: bool;
  age: number;
  fun feed() { fed = true }
  fun talk(text: string) { print(text) }
  fun name(): string = "Bello"
}


// The programming language also supports pre- and postconditions
// (also known as variant and covariant). The application will throw
// an error if one of those is not fullfilled.
fun add(a: number, b: number) requires (a > 0 && b > 0) ensures (_ >= (a + b)) {
  return a + b;
}

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

  // var dog = new Dog;
  // var dog = new Dog { fed: false };
  var dog = new Dog { false };

  // There is only the "loop" in this programming language. No
  // for, while or do-whiles. You can do everything with this.
  loop 1..10 {
    if (dog.fed) {
      break;
    }
    dog.feed()
  }

  // When is similar to switch-case in other languages
  when dog.fed {
    true:  { print("Yummy, I am full!") }
    false: { print("I want some more food") }
    else:  { print("This is not possible") }
  }

  print(dog.fed);       // true
  print(dog == dog);    // true
  print(~(-7+3**2));    // -3

  // Starts the given block in a coroutine
  run { dog.feed() }

  printHelloWorld()
}
```