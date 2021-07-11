# Programming Language

## Grammar

The formal language definition looks like the following. Feel free to implement it yourself.

```
<statement-list> ::= statement
                   | statement SEMICOLON statement-list

<assign> ::= VAR name ASSIGN expr
           | CONST name ASSIGN expr

<expr> ::= NOT logical-expr
         | logical-expr (( AND | OR ) logical-expr)*

<if-expr> ::= IF expr LBRACE expr RBRACE
              (ELSE IF expr LBRACE expr RBRACE)*
              (ELSE LBRACE expr RBRACE)?

<logical-expr> ::= arithmetic-expr (( EQ | LT | LTE | GT | GTE ) arithmetic-expr)*
<arithmetic-expr> ::= term (( PLUS | MINUS ) term)*

<term> ::= atom (( MULT | DIVIDE ) atom)*
<name> ::= IDENTIFIER
<atom> ::= PLUS number
         | MINUS number
         | COMPLEMENT number
         | LPAREN expr RPAREN
         | if-expr

<empty> ::= ()
```

## Example

```
#pragma precision 12

package MyProgram;

import System.IO;
import Math;


trait Feedable {
  fun feed();
}

trait CanTalk {
  fun talk(text: string);
}

class Dog is Feedable, CanTalk {
  var fed = false;
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

// The entry keyword specifies the function that should
// be executed on program start.
entry fun main() {
  // var dog = new Dog;
  // var dog = new Dog { fed: false };
  var dog = new Dog { false };
  var num = 3.141592654;

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