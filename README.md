[![CI](https://github.com/danielkleebinder/nyxlang/actions/workflows/main.yml/badge.svg)](https://github.com/danielkleebinder/nyxlang/actions/workflows/main.yml)

# Nyx Programming Language

![Nyx Icon](https://github.com/danielkleebinder/nyxlang/blob/main/nyxlang.png?raw=true)

The nyx programming language (also known as nyxlang) is a statically and strongly typed, lexically scoped and interpreted programming language that allows the developer
to implement traits and custom types. It is object oriented, but does not support inheritance since inheritance is a common source of
maintainability issues.


(influenced by TypeScript, Kotlin, Go, Eiffel, ML)

## Grammar

The formal language definition in Backus-Naur form looks like the following. Feel free to implement it yourself.

### Statements

```
<program>    ::= <statements>
<block>      ::= '{' (NL)* <statements> (NL)* '}'

<statements> ::= <statement> ((';' | (NL)*) <statement>)*
<statement>  ::= ('const' | 'var') (NL)* <declaration>
               | 'return' ((NL)* <expr>)?
               | 'break'
               | 'continue'
               | <block>
               | <fun-declaration>
               | <expr>
```

### Functions
```
<fun-declaration> ::= 'fun' (NL)* (<name> (NL)*)?
                       (<fun-params> (NL)*)?
                       (<fun-requires> (NL)*)?
                       (<fun-ensures> (NL)*)?
                       (<fun-returns> (NL)*)?
                       (<fun-body>)

<fun-params>   ::= '(' (NL)* <declarations> (NL)* ')'
<fun-requires> ::= ':' (NL)* <expr>
<fun-ensures>  ::= ':' (NL)* <expr>
<fun-return>   ::= '->' (NL)* <type>
<fun-body>     ::= <block> | ('=' (NL)* <expr>)
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

### Declarations
```
<declarations> ::= <declaration> (NL)* (',' (NL)* <declaration>)*
<declaration>  ::= <name> (NL)* (':' (NL)* <type>)? ('=' (NL)* <expr>)?
```

### Expressions & Precedence
```
<expr>     ::= <equ-expr> ((NL)* ( '&&' | '||' ) (NL)* <equ-expr>)*
<equ-expr> ::= <rel-expr> ((NL)* ( '==' | '!=' ) (NL)* <rel-expr>)*
<rel-expr> ::= <ran-expr> ((NL)* ( '<' | '<=' | '>' | '>=' ) (NL)* <ran-expr>)*
<ran-expr> ::= <add-expr> ((NL)* ( '..' ) (NL)* <add-expr>)*
<add-expr> ::= <mul-expr> ((NL)* ( '+' | '-' ) (NL)* <mul-expr>)*
<mul-expr> ::= <prefix-expr> ((NL)* ( '*' | '/' | '%' ) (NL)* <prefix-expr>)*
<arr-expr> ::= '[' (NL)* (<expr> ((NL)* ',' (NL)* <expr>)*)? (NL)* ']'

<prefix-expr> ::= ('!' | '+' | '-' | '~')? <postfix-expr>
<postfix-expr> ::= <atom> (<suffix>)?
```

### Suffix
```
<suffix> ::= ('++' | '--' | '?')
           | <assign-suffix>
           | <index-suffix>
           | <call-suffix>

<assign-suffix> ::= '=' (NL)* <expr>
<index-suffix>  ::= '[' (NL)* <expr> (NL)* ']'
<call-suffix>   ::= '(' (NL)* (<expr> ((NL)* ',' (NL)* <expr>))? (NL)* ')'
```

### Atom & Delegation
```
<atom> ::= <bool> | <number> | <string> | <name>
         | '(' <expr> ')'
         | <arr-expr>
         | <if-expr>
         | <loop-stmt>
         | <empty>

<type>  ::= <number> | <bool> | <string>
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

In addition to the primitive functions, the µ-recursive operators must be supported as well:

- **Composition operator**. Given a function `h(x1, ...xm)` and m k-ary function `g1(x1, ..., xk), ... gm(x1, ..., xk)`, then `h ° (g1, ..., gm) = f` can be shown.
- **Recursion operator**. Given a function 
- **Minimization operator**.

It is left to write those three µ-recursive functions and the three operators in this programming language to prove
Turing completeness and therefore interchangeability with any other Turing machine.

### µ-recursive functions in nyxlang
This section shows that µ-recursive functions can be defined in nyxlang.

#### Constant Function
A reduction of the constant function is the zero function which always returns `0` instead of `n`. This can be shown
as follows:

```
fun c(arr: number[]) -> number = 0
```

#### Successor Function
The successor function returns the successor value of the given parameter. Since numbers are sufficient for µ-recursive
functions, simply increasing the value completes the task.

```
fun s(x: number) -> number = (x + 1)
```

#### Projection Function
The projection function requires that the value of `i` is in the range of the arrays size. This can be assured by using
the `requires` function property of the programming language.

```
fun p(i: number, arr: number[]) : (0 <= i && i < arr.size) -> number = arr[i]
```

### Operators in nyxlang
This section shows that the previously defined operators can be implemented in nyxlang.

#### Composition Operator
One can prove the existence of the composition operator without loss of generality by showing that a function `g` can be
defined that takes a specific number of arguments `K` and another function `h` that takes `M` arguments. Such a definition
and implementation might look like the following.

Let `K = 2` and let `M = 2`, then we can construct three functions that allow for composition (w.l.o.g):

```
fun h(a1: number, a2: number, ...) = ...
fun g(b1: number, b2: number, ...) = ...
fun f(x1: number, x2: number) = h(g(x1, x2, ...), g(x1, x2, ...))
```

(the function implementation for `g` and `h` are irrelevant for this proof)

#### Recursion Operator

#### Minimization Operator

Therefore, the proof is done and Turing completeness has been shown.


## Example Programs

```kotlin
program test

use "system.io"
use "math"


trait Feedable {
  fun feed()
}

trait CanTalk {
  fun CanTalk.talk(text: string)
}

type Dog is Feedable, CanTalk {

  fed: bool, happy: bool
  age: number

  fun feed = fed = true
  fun name() -> string = 'Bello'
  fun talk(text: string) = print(name . ' says: ' . text)

}



///
The programming language also supports pre- and postconditions
(also known as variant and covariant). The application will throw
an error if one of those is not fullfilled.

The first statement after the parameter list and after the ':' symbol
is the precondition that the parameters have to fulfill. The statement
after that is the postcondition. The '_' is a reserved token and
represents the functions result.
\\\
fun add(a: number, b: number) : (a > 0 && b > 0) : (_ >= (a + b)) -> number = a + b
fun sub(a: number, b: number) :: (_ <= (a - b)) -> number = a - b

// Simple function without pre- or postcondition
fun greeting(msg: string) {
  // Runs the code block in the interpreters native language
  native('System.out.println("Hello ' . msg . '")')
}

// The "main" function is the entry point into the program
fun main {

  // Constants must be initialized on declaration and cannot
  // be modified at a later stage.
  const pi: number = 3.141592654;
  const square = fun (x: number) -> number = x * x
  const myGreeting = greeting

  // var dog = Dog;
  // var dog = Dog { fed: false };
  var dog = Dog { false }

  // There is only the "loop" in this programming language. No
  // for, while or do-whiles. You can do everything with this.
  loop var i = 0 :: ++i {
    if dog.fed {
      break
    }
    dog.feed()
  }

  loop !dog.fed {
    dog.feed()
  }

  // Use the print function from system.io
  print(dog.fed)       // true
  print(dog == dog)    // true
  print(~(-7+3**2))    // -3

  // Starts the given block in a coroutine
  async { dog.feed() }
  async dog.feed()
  async 25 * 3

  greetings("Peter Parker")
}
```

#### Recursion
The following program shows a very simple example on how recursion can be implemented. The function does
not return any value and is invoked 5 times:

```kotlin
fun recursion(a: number) = if a > 0 { recursion(a - 1) }
recursion(5)
```