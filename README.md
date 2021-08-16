![Leaf Icon](https://github.com/danielkleebinder/leaf-lang/blob/main/leaf-lang-icon.png?raw=true)

# The Leaf Programming Language

[![Build](https://github.com/danielkleebinder/leaf-lang/actions/workflows/main.yml/badge.svg)](https://github.com/danielkleebinder/leaf-lang/actions/workflows/main.yml)
[![Licence](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/danielkleebinder/leaf-lang/blob/main/LICENSE)

[Leaf programming language website](https://danielkleebinder.github.io/leaf-lang.html)

The Leaf programming language is a statically and strongly typed, lexically scoped and interpreted programming language using
type inference that allows the developer to implement traits and custom types. It is object oriented, but does not support
inheritance because typically, it is a common source of maintainability issues.

This programming language was solely written by me for educational purposes only. It should showcase some concepts I
learned in my Masters computer science courses at TU Vienna. Although I am sure it could technically be used as production
ready language, I would not recommend it yet ;-)

(influenced by TypeScript, Kotlin, Go, Eiffel, ML)


## Grammar

The formal language definition in **Backus-Naur form** looks like the following. Feel free to implement it yourself.

### Statements

```
<program>    ::= <statements>
<block>      ::= '{' (NL)* <statements> (NL)* '}'

<statements> ::= <statement> ((';' | (NL)*) <statement>)*
<statement>  ::= ('const' | 'var') (NL)* <declaration>
               | 'async' <statement>
               | 'return' ((NL)* <expr>)?
               | 'break'
               | 'continue'
               | <trait-declaration>
               | <type-declaration>
               | <assignment>
               | <loop-stmt>
               | <expr>
```

### Functions
```
<fun-declaration> ::= 'fun' (NL)* (<fun-extension> (NL)*)? (<name> (NL)*)?
                       (<fun-params> (NL)*)?
                       (<fun-requires> (NL)*)?
                       (<fun-ensures> (NL)*)?
                       (<fun-returns> (NL)*)?
                       (<fun-body>)?

<fun-extension> ::= '<' <type> (NL)* (',' (NL)* <type> (NL)*)* '>' (NL)* '.'
<fun-params>    ::= '(' (NL)* <declarations> (NL)* ')'
<fun-requires>  ::= ':' (NL)* <expr>
<fun-ensures>   ::= ':' (NL)* <expr>
<fun-return>    ::= '->' (NL)* <type>
<fun-body>      ::= <block>
                  | ('=' (NL)* <statement>)
```

### Conditionals
```
<if-expr> ::= 'if' ((NL)* <expr> (NL)* <block>)
                   ((NL)* <else-if>)*
                   ((NL)* <else>)?

<else-if> ::= 'else' (NL)* 'if' (NL)* <expr> (NL)* <block>
<else>    ::= 'else' (NL)*                         <block>
```

### Loops
```
<loop-stmt> ::= 'loop' ((NL)* <loop-init>)?
                       ((NL)* <loop-cond>)?
                       ((NL)* <loop-step>)?
                       ((NL)* <loop-body>)

<loop-init> ::= <statement>
<loop-cond> ::= ':' (NL)* <expr>
<loop-step> ::= ':' (NL)* <statement>
<loop-body> ::= <block>
```

### Custom Types
```
<trait-declaration> ::= 'trait' (NL)* <name> (NL)*
<type-declaration>  ::= 'type' (NL)* <name> (NL)*
                        (<trait-list>)?
                        (<type-body>)?

<trait-list> ::= ':' (NL)* <name> (NL)* (',' (NL)* <name> (NL)*)* 
<type-body>  ::= '{' (NL)* (<declarations> (NL)*)* '}'

<type-inst>  ::= 'new' <name> ('{' (NL)* <inst-body> (NL)* '}')?
<inst-body>  ::= (<inst-value> (NL)* (',' (NL)* <inst-value> (NL)*)* )?
<inst-value> ::= (<name> '=')? <expr>
```

### Declarations
```
<declarations> ::= <declaration> (NL)* (',' (NL)* <declaration>)*
<declaration>  ::= <name> (NL)* (':' (NL)* <type>)? ('=' (NL)* <expr>)?
<assignment>   ::= <var> ('=' <expr>)?
```

### Expressions & Precedence
```
<expr>     ::= <equ-expr> ((NL)* ( '&&' | '||' ) (NL)* <equ-expr>)*
<equ-expr> ::= <rel-expr> ((NL)* ( '==' | '!=' ) (NL)* <rel-expr>)*
<rel-expr> ::= <ran-expr> ((NL)* ( '<' | '<=' | '>' | '>=' ) (NL)* <ran-expr>)*
<ran-expr> ::= <add-expr> ((NL)* ( '..' ) (NL)* <add-expr>)*
<add-expr> ::= <mul-expr> ((NL)* ( '+' | '-' ) (NL)* <mul-expr>)*
<mul-expr> ::= <prefix-expr> ((NL)* ( '*' | '/' | '%' ) (NL)* <prefix-expr>)*
<pre-expr> ::= ('!' | '+' | '-' | '~')? <atom>
<arr-expr> ::= '[' (NL)* (<expr> ((NL)* ',' (NL)* <expr>)*)? (NL)* ']'
```

### Variables
```
<var> ::= <name>
        | <name> '.' <name>
        | <name> '[' (NL)* <expr> (NL*) ']'
        | <name> '(' (NL)* (<expr> ((NL)* ',' (NL)* <expr>))? (NL)* ')'
```

### Atom & Delegation
```
<atom> ::= <bool> | <number> | <string> | <var>
         | '(' <expr> ')'
         | <assignment>
         | <type-inst>
         | <arr-expr>
         | <if-expr>
         | <fun-declaration>
         | <coroutine>
         | <block>
         | <empty>

<name>  ::= IDENTIFIER
<type>  ::= <number> | <bool> | <string> | <array> | <fun> | <name>
<empty> ::= ()
```


## Turing Completeness
Turing completeness can be shown by implementing a mapping between the WHILE and the Leaf programming language. The WHILE
programming language is proven to be Turing complete and is defined as follows:

```
C ::= <L> := <E>
    | 'if' <B> 'then' <C> 'else' <C>
    | 'while' <B> 'do' <C>
    | <C>;<C>
    | skip

B ::= 'true' | 'false' | <E> '=' <E> | <B> '&' <B> | '-' <B>
E ::= L | n | (E + E)
```

The (bidirectional) mapping from WHILE to Leaf can be implemented as isomorphism function and looks like the following (WHILE
on the left hand side and Leaf on the right hand side):

```
<C> <=> <declaration> | <assignment>
      | <if-expr>
      | <loop-stmt>
      | <statements>
      | <empty>

<B> <=> 'true' | 'false' | <expr>
<E> <=> <expr>
<L> <=> <name>
```

Therefore, every program written in WHILE can also be expressed in Leaf without loss of semantic meaning. The proof is
complete and Turing completeness has been shown.


## Example Programs

```kotlin
program "test"

use "io", "math"

trait Feedable
trait CanTalk

type Cat : CanTalk
type Dog : Feedable, CanTalk {
  fed: bool, happy: bool
  age: number
}

// Types implement functions by specifying them as extensions
fun <Cat>.name() -> string = 'Kitty'
fun <Dog>.name() -> string = 'Bello'
fun <Dog>.feed() = object.fed = true
fun <Dog, Cat>.talk(text: string) = print(object.name() + ' says: ' . text)


//
// The programming language also supports pre- and postconditions
// (also known as variant and covariant). The application will throw
// an error if one of those is not fullfilled.
//
// The first statement after the parameter list and after the ':' symbol
// is the precondition that the parameters have to fulfill. The statement
// after that is the postcondition. The '_' is a reserved token and
// represents the functions result.
//
fun add(a: number, b: number) : (a > 0 && b > 0) : (_ >= (a + b)) -> number = a + b
fun sub(a: number, b: number) :: (_ <= (a - b)) -> number = a - b

// The "main" function is the entry point into the program
fun main {

  // Constants must be initialized on declaration and cannot
  // be modified at a later stage.
  const pi: number = 3.141592654;
  const square = fun (x: number) -> number = x * x
  const myGreeting = greeting

  // var dog = Dog;
  // var dog = Dog { fed = false };
  var dog = Dog { false }

  // There is only the "loop" in this programming language. No
  // for, while or do-whiles. You can do everything with this.
  loop var i = 0 :: i = i + 1 {
    if dog.fed { break }
    dog.feed()
  }

  loop !dog.fed {
    dog.feed()
  }

  // Use the print function from io
  print(dog.fed)       // true
  print(dog == dog)    // true
  print(~(-7+3**2))    // -3
}
```

### Recursion
The following program shows a very simple example on how recursion can be implemented. The function does
not return any value and is invoked 5 times:

```kotlin
fun recursion(a: number) = if a > 0 { recursion(a - 1) }
recursion(5)
```

### Lambdas
Lambdas are typically used to implement some sort of functional programming pattern that focuses on the
computational part of a problem. Lambdas can be used as follows:

```kotlin
fun compute(a: fun, b: fun) -> number = a(5,5) * b(10,8)

// Result is 20
compute(
  fun (a: number, b: number) -> number = a * b,
  fun (a: number, b: number) -> number = a % b)
```

### Types
Besides the standard data types like `number` or `bool`, the language also supports custom data types that can be
extended with functions. Function definitions are not allowed in the custom type definition body but must be defined
outside. The following example shows a typical type definition:

```kotlin
type Dog {
  name: string
}

type Human {
  name: string
  age: 27
}
```

These types can be extended using functions:

```kotlin
fun <Human, Dog>.sayHi -> string = object.name + " says Hi!"
```

Extension functions can be applied on multiple types at the same time. Leaf will use dynamic structural typing
on `object` if for the case above. This function can be executed like:

```kotlin
const me = new Human { "Daniel" }
me.sayHi()
```