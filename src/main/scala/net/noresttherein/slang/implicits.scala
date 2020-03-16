package net.noresttherein.slang

import net.noresttherein.slang.numeric.LongRatio.DivisionLongRatioConstructor
import net.noresttherein.slang.numeric.Ratio.DivisionRatioConstructor

import net.noresttherein.slang.tuples.{RichTuple10, RichTuple11, RichTuple12, RichTuple13, RichTuple14, RichTuple15, RichTuple16, RichTuple17, RichTuple18, RichTuple19, RichTuple2, RichTuple20, RichTuple21, RichTuple22, RichTuple3, RichTuple4, RichTuple5, RichTuple6, RichTuple7, RichTuple8, RichTuple9}



/** Type aliases and forwarders for most useful types and implicit conversions in the library providing new syntax.
  * It is recommended to import those declarations from here rather than the package of their actual definition.
  * Not only this saves the user searching the packages for a class before its use, but the import clause will
  * explicitly name the members as implicit, making it also easier to find the applied conversion in existing code.
  * As a further mnemonic, class and method members here are (re)named after the most prominent declared method.
  * Finally, the lazy and the brave can import (almost) all new syntax with a single wildcard statement.
  * @author Marcin Mościcki marcin@moscicki.net
  */
object implicits {
	import optional._
	import repeatedly._
	import prettyprint._

	/** Adds a `useIn` method to any value which applies a given function to `this`. */
	implicit class useInMethod[X](private val x :X) extends AnyVal {
		/** Applies the argument function to the 'self' argument. As self is eagerly computed, `expr useIn f`
		  * is equivalent to `{ val x = expr; f(x) }`, but may be more succinct and convenient to write,
		  * especially when modifying existing code for `f`, as there is no need for a a closing `}` in a possibly
		  * distant edit location.
		  */
		def useIn[T](f :X => T) :T = f(x)
	}




	/** Adds `ifTrue` and `ifFalse` methods to any `Boolean` value which lift any argument expression to an `Option`. */
	@inline implicit final def ifTrueMethods(condition :Boolean) :ifTrueMethods = new ifTrueMethods(condition)

	/** Adds `satisfying` and `dissatisfying` methods to any object for lifting it to an `Option[T]` based on a predicate value. */
	@inline implicit final def satisfyingMethods[T](subject :T) :satisfyingMethods[T] = new satisfyingMethods(subject)

	/** Adds `providing` and `unless` methods to any lazy expression, returning it as an option after testing a boolean condition. */
	@inline implicit final def providingMethods[T](subject : =>T) :providingMethods[T] = new providingMethods[T](subject)

	/** Adds a `some` accessor method to `Some` emphasising that the call is safe, unlike the inherited `get` available
	  * also on `None`.
	  */
	@inline implicit final def someMethod[T](opt :Some[T]) :someMethod[T] = new someMethod(opt)

	/** Adds additional `ensuring` methods to any object which accept exception classes to throw on failure. */
	@inline implicit final def ensuringMethods[T](subject :T) :ensuringMethods[T] = new ensuringMethods(subject)



	/** Adds a `foldWhile` method to any `Iterable` which implement a variant of `fold` operation with a break condition. */
	@inline implicit final def foldWhileMethods[T](col :Iterable[T]) :foldWhileMethods[T] = new foldWhileMethods(col)

	/** Adds a `times` method to any `Int` for executing a block the given number of times. */
	@inline implicit final def timesMethods(iterations :Int) :timesMethods = new timesMethods(iterations)



	/** Adds `localClassName` and `shortClassName` methods to any object providing a shorter alternative to `getClass.getName`. */
	@inline implicit final def classNameMethods(any :Any) :classNameMethods = new classNameMethods(any)

	/** Adds methods converting the fields of this object to a `String` via reflection for the use in `toString` methods. */
	@inline implicit final def fieldsStringMethods[T](obj :T) = new ObjectFieldsFormats(obj)

	/** Adds a `yesno` and `yn` methods to `Boolean` values for shorter `String` representations. */
	@inline implicit final def yesnoMethod(boolean :Boolean) = new YesNo(boolean)


	/** Implicit conversion extending `Long` values with a `/%` method accepting other another `Long` and
	  * constructing a [[net.noresttherein.slang.numeric.LongRatio]] instance as an alternative to LongRatio object's
	  * factory method. If you wish to perform other arithmetic operations with `Long` values as the left-hand argument
	  * use the appropriate right-associative method of the `LongRatio` class.
	  * @param numerator this integer, serving as thee numerator of the future rational
	  * @return a builder object accepting the denominator for the rational result.
	  */
	@inline implicit def /%(numerator :Long) :DivisionLongRatioConstructor = new DivisionLongRatioConstructor(numerator)

	/** Implicit conversion extending `Int` values with a `/%` method accepting other another `Int` and
	  * constructing a [[net.noresttherein.slang.numeric.Ratio]] instance as an alternative to LongRatio object's
	  * factory method. If you wish to perform other arithmetic operations with `Long` values as the left-hand argument
	  * use the appropriate right-associative method of the `Ratio` class.
	  * @param numerator this integer, serving as thee numerator of the future rational
	  * @return a builder object accepting the denominator for the rational result.
	  */
	@inline implicit def /%(numerator :Int) :DivisionRatioConstructor = new DivisionRatioConstructor(numerator)


	/** Adds `++`, `:+` and `+: methods to `Tuple2`.` */
	@inline implicit def tupleConcat[T1, T2](t :(T1, T2)) :RichTuple2[T1, T2] = new RichTuple2(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple3`.` */
	@inline implicit def tupleConcat[T1, T2, T3](t :(T1, T2, T3)) :RichTuple3[T1, T2, T3] = new RichTuple3(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple4`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4](t :(T1, T2, T3, T4)) :RichTuple4[T1, T2, T3, T4] = new RichTuple4(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple5`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5](t :(T1, T2, T3, T4, T5)) :RichTuple5[T1, T2, T3, T4, T5] = new RichTuple5(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple6`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6](t :(T1, T2, T3, T4, T5, T6)) :RichTuple6[T1, T2, T3, T4, T5, T6] = new RichTuple6(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple7`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7](t :(T1, T2, T3, T4, T5, T6, T7)) :RichTuple7[T1, T2, T3, T4, T5, T6, T7] = new RichTuple7(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple8`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8](t :(T1, T2, T3, T4, T5, T6, T7, T8)) :RichTuple8[T1, T2, T3, T4, T5, T6, T7, T8] = new RichTuple8(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple9`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9)) :RichTuple9[T1, T2, T3, T4, T5, T6, T7, T8, T9] = new RichTuple9(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple10`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10)) :RichTuple10[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10] = new RichTuple10(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple11`. */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11)) :RichTuple11[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11] = new RichTuple11(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple12`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12)) :RichTuple12[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12] = new RichTuple12(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple13`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13)) :RichTuple13[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13] = new RichTuple13(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple14`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14)) :RichTuple14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14] = new RichTuple14(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple15`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15)) :RichTuple15[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15] = new RichTuple15(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple16. */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16)) :RichTuple16[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16] = new RichTuple16(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple17`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17)) :RichTuple17[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17] = new RichTuple17(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple18`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18)) :RichTuple18[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18] = new RichTuple18(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple19`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19)) :RichTuple19[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19] = new RichTuple19(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple20`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20)) :RichTuple20[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20] = new RichTuple20(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple21`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21)) :RichTuple21[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21] = new RichTuple21(t)

	/** Adds `++`, `:+` and `+: methods to `Tuple22`.` */
	@inline implicit def tupleConcat[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](t :(T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22)) :RichTuple22[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22] = new RichTuple22(t)

}
