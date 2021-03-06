![Leaf Icon](https://github.com/danielkleebinder/leaf-lang/blob/main/leaf-lang-icon.png?raw=true)

# The Leaf Programming Language

[![Build](https://github.com/danielkleebinder/leaf-lang/actions/workflows/main.yml/badge.svg)](https://github.com/danielkleebinder/leaf-lang/actions/workflows/main.yml)
[![Code Coverage](https://img.shields.io/badge/coverage-83%25-green)](https://github.com/danielkleebinder/leaf-lang/tree/main/src/test)
[![Licence](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://github.com/danielkleebinder/leaf-lang/blob/main/LICENSE)
[![Issues](https://img.shields.io/github/issues/danielkleebinder/leaf-lang?maxAge=2592000)](https://github.com/danielkleebinder/leaf-lang/issues)

[Leaf programming language website](https://danielkleebinder.github.io/leaf-lang.html)

The Leaf programming language is a statically and strongly typed, lexically scoped and interpreted programming language using
type inference that allows the developer to implement traits and custom types. It is object oriented (i.e. supports subtyping and
polymorphism), but does not support inheritance because typically, it is a common source of maintainability issues.

This programming language was written for educational purposes only. It showcases some concepts I
learned in my Masters computer science courses at TU Vienna.

(influenced by TypeScript, Kotlin, Go, Eiffel, ML)


## Grammar

The formal language definition in **Backus-Naur form** looks like the following. Feel free to implement it yourself.

### Statements

```
<program>    ::= <statements>
<block>      ::= '{' (NL)* <statements> (NL)* '}'

<statements> ::= <statement> ((';' | (NL)*) <statement>)*
<statement>  ::= ('const' | 'var') (NL)* <declaration>
               | 'return' ((NL)* <expr>)?
               | 'break'
               | 'continue'
               | <trait-declaration>
               | <type-declaration>
               | <assignment>
               | <use-stmt>
               | <loop-stmt>
               | <expr>

<use-stmt> ::= 'use' (NL)* '(' (NL)* <string> (NL)* (',' (NL)* <string> (NL)*)*  ')'
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
        | <name> (NL)* '.' (NL)* <name>
        | <name> (NL)* '[' (NL)* <expr> (NL*) ']'
        | <name> (NL)* '(' (NL)* (<expr> ((NL)* ',' (NL)* <expr>))? (NL)* ')'
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
<C> ::= <L> := <E>
      | 'if' <B> 'then' <C> 'else' <C>
      | 'while' <B> 'do' <C>
      | <C>;<C>
      | skip

<B> ::= 'true' | 'false' | <E> '=' <E> | <B> '&' <B> | '-' <B>
<E> ::= <L> | n | (E + E)
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
use (
  "libs/my-math-lib.leaf",
  "libs/my-stream-lib.leaf",
  "libs/my-http-lib.leaf"
)


trait Eats
fun <Eats>.eat()
fun <Eats>.hungry()

trait Talks
fun <Talks>.talk()

type Dog : Eats, Talks { fed: bool }
type Cat : Eats, Talks { fed: bool }

// Types implement functions by specifying them as extensions
fun <Dog>.talk = println("Wuff wuff")
fun <Cat>.talk = println("Miau Purrrr...")

fun <Cat, Dog>.hungry = !object.fed
fun <Cat, Dog>.eat {
  object.fed = true
  println("That was delicious")
}

//
// The programming language also supports pre- and postconditions
// (also known as variant and covariant). The application will throw
// an error if one of those is not fullfilled.
//
// The first statement after the parameter list and after the ':' symbol
// is the precondition that the parameters have to fulfill. The statement
// after that is the postcondition. The '_' is a reserved token and
// represents the returned value.
//
fun feed(eater: Eats) : eater.hungry() : !(_.hungry()) -> Eats {
  eater.eat()
  return eater
}

fun main {
  // Constants must be initialized on declaration and cannot
  // be modified at a later stage.
  const pi: number = 3.141592654;
  const square = fun (x: number) -> number = x * x
  const pet = new Dog { false }
  
  loop pet.hungry() {
    feed(pet)
  }
  
  const num = 77
  if square(num) < 10 {
    println("Square root of " + num + " is less than 10")
  }
  
  println(pet)
  println(~(-7+3*9))    // -21
}

main()
```

### Recursion
The following program shows a very simple example on how recursion can be implemented. The function does
not return any value and is invoked 5 times:

```kotlin
fun recursion(a: number) = if a > 0 { recursion(a - 1) }
recursion(5)
```

### If-Expressions
Other programming languages often times only implement if-statements. This means, that `if` cannot be used to return any
value directly. This programming language (similar to Kotlin), however, provides if-expressions.

```kotlin
const someValue = ...
const res = if someValue > 0 {
  someValue * 100
} else {
  0
}
```

This can be very useful in combination with single expression functions (like in the recursion example above). It also
allows for rather weird and hard to comprehend expressions. So it is up to the developer to use them with caution. The
example below is a correct and sound statement where `res` will be `10` after execution:

```kotlin
const res = if if if if true { true } else { false } { true } { 1 == 1 } { 10 }
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
  age = 27
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

### Traits
Besides basic types, the programming language also supports so called traits. A `trait` is a special kind of type that
allows design by contract. Using traits, different developers can create separate modules while still enforcing certain
requirements. A `trait` can be compared to an `interface` in programming languages like Kotlin or TypeScript.

```kotlin
trait ComparesPositive
fun <ComparesPositive>.compare(n: number) : n > 0 -> number
```

A custom `type` then has to implement this trait to allow proper subtyping. Traits are also one of the fundamental
aspects when it comes to object-oriented programming paradigms in this programming language:

```kotlin
type Age : ComparesPositive {
  age: number
}

fun <Age>.compare(n: number) {
  return object.age - n
}
```


## References

* [Programming Language Concepts by Carlo Ghezzi and Mehdi Jazayeri](https://www.wiley.com/en-us/Programming+Language+Concepts%2C+3rd+Edition-p-9780471104261)
* [Writing Compilers and Interpreters: A Software Engineering Approach](https://www.wiley.com/en-us/Writing+Compilers+and+Interpreters%3A+A+Software+Engineering+Approach%2C+3rd+Edition-p-9780470177075)
* [The While Programming Language](http://profs.sci.univr.it/~merro/files/WhileExtra_l.pdf)
* [Let's Build a Simple Interpreter](https://ruslanspivak.com/lsbasi-part1/)
* [Go Language Specification](https://golang.org/ref/spec)
* [Kotlin Language Specification](https://kotlinlang.org/spec/introduction.html)
* [ECMAScript Language Specification](https://262.ecma-international.org/)