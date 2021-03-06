package net.noresttherein.slang.vars

import net.noresttherein.slang.vars.InOut.{DefaultValue, SpecializedVars, TypeEquiv}




/** A simple `@specialized` boxed `var`. Allows for in/out parameters to functions.
  * Implicit conversions exist providing arithmetic suitable to the type of the boxed value, so for example
  * you can write `param += 1` for `param :Var[Int]`. The use of this class directly over through the `In/Out`
  * interface may be preferable to reduce the number of virtual calls and possibly facilitate hotspot inlining.
  * @tparam T type of this variable
  * @see [[net.noresttherein.slang.vars.Var$]]
  */
@SerialVersionUID(1L)
sealed class Var[@specialized(SpecializedVars) T](private[this] var x :T) extends InOut[T] with Serializable {

	/** Current value of this variable. */
	override def value :T = x

	/** Assigns a new value to this variable. */
	override def value_=(newValue :T) :Unit = x = newValue

	/** Assigns a new value returning the previous value at the same time. */
	override def ?=(newValue :T) :T = { val res = x; x = newValue; res }

	/** Compares the current value of this variable with the first argument and, if they are equal, sets this variable
	  * to the second argument
	  * @param expect value to compare with current value
	  * @param assign new value for this variable
	  * @return `true` if previous value equaled `expect` and the variable has been set to `assign`.
	  */
	override def testAndSet(expect :T, assign :T) :Boolean =
		(x == expect) && {x = assign; true}



	/************************************** Boolean methods ***********************************************************/

	private[vars] override def bool_&=(other :Boolean)(implicit ev :T TypeEquiv Boolean) :Unit = {
		val self = ev(this)
		self.value = self.value & other
	}

	private[vars] override def bool_|=(other :Boolean)(implicit ev :T TypeEquiv Boolean) :Unit = {
		val self = ev(this)
		self.value = self.value | other
	}

	private[vars] override def bool_&&=(other: => Boolean)(implicit ev :T TypeEquiv Boolean) :Unit = {
		val self = ev(this)
		self.value = self.value && other
	}

	private[vars] override def bool_||=(other: =>Boolean)(implicit ev :T TypeEquiv Boolean) :Unit = {
		val self = ev(this)
		self.value = self.value || other
	}

	private[vars] override def bool_^=(other :Boolean)(implicit ev :T TypeEquiv Boolean) :Unit = {
		val self = ev(this)
		self.value = self.value ^ other
	}

	private[vars] override def bool_!=(implicit ev :T TypeEquiv Boolean) :Boolean = {
		val self = ev(this)
		val res = !self.value; self.value = res; res
	}


	/************************************** Int methods ***************************************************************/

	private[vars] override def int_+=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value + other; self.value = res; res
	}

	private[vars] override def int_*=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value * other; self.value = res; res
	}

	private[vars] override def int_/=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value / other; self.value = res; res
	}

	private[vars] override def int_%=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value % other; self.value = res; res
	}

	private[vars] override def int_-(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = -self.value; self.value = res; res
	}

	private[vars] override def int_~(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = ~self.value; self.value = res; res
	}

	private[vars] override def int_|=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value | other; self.value = res; res
	}

	private[vars] override def int_&=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value & other; self.value = res; res
	}

	private[vars] override def int_^=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value ^ other; self.value = res; res
	}

	private[vars] override def int_>>=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value >> other; self.value = res; res
	}

	private[vars] override def int_>>>=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value >>> other; self.value = res; res
	}

	private[vars] override def int_<<=(other :Int)(implicit ev :T TypeEquiv Int) :Int = {
		val self = ev(this)
		val res = self.value << other; self.value = res; res
	}


	/************************************** Long methods **************************************************************/

	private[vars] override def long_+=(other :Long)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value + other; self.value = res; res
	}

	private[vars] override def long_*=(other :Long)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value * other; self.value = res; res
	}

	private[vars] override def long_/=(other :Long)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value / other; self.value = res; res
	}

	private[vars] override def long_%=(other :Long)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value % other; self.value = res; res
	}

	private[vars] override def long_-(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = -self.value; self.value = res; res
	}

	private[vars] override def long_~(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = ~self.value; self.value = res; res
	}

	private[vars] override def long_|=(other :Long)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value | other; self.value = res; res
	}

	private[vars] override def long_&=(other :Long)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value & other; self.value = res; res
	}

	private[vars] override def long_^=(other :Long)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value ^ other; self.value = res; res
	}

	private[vars] override def long_>>=(n :Int)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value >> n; self.value = res; res
	}

	private[vars] override def long_>>>=(n :Int)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value >>> n; self.value = res; res
	}

	private[vars] override def long_<<=(n :Int)(implicit ev :T TypeEquiv Long) :Long = {
		val self = ev(this)
		val res = self.value << n; self.value = res; res
	}


	/************************************** Float methods *************************************************************/

	private[vars] override def float_+=(other :Float)(implicit ev :T TypeEquiv Float) :Float = {
		val self = ev(this)
		val res = self.value + other; self.value = res; res
	}

	private[vars] override def float_*=(other :Float)(implicit ev :T TypeEquiv Float) :Float = {
		val self = ev(this)
		val res = self.value * other; self.value = res; res
	}

	private[vars] override def float_/=(other :Float)(implicit ev :T TypeEquiv Float) :Float = {
		val self = ev(this)
		val res = self.value / other; self.value = res; res
	}

	private[vars] override def float_-(implicit ev :T TypeEquiv Float) :Float = {
		val self = ev(this)
		val res = -self.value; self.value = res; res
	}


	/************************************** Double methods ************************************************************/

	private[vars] override def double_+=(other :Double)(implicit ev :T TypeEquiv Double) :Double = {
		val self = ev(this)
		val res = self.value + other; self.value = res; res
	}

	private[vars] override def double_*=(other :Double)(implicit ev :T TypeEquiv Double) :Double = {
		val self = ev(this)
		val res = self.value * other; self.value = res; res
	}

	private[vars] override def double_/=(other :Double)(implicit ev :T TypeEquiv Double) :Double = {
		val self = ev(this)
		val res = self.value / other; self.value = res; res
	}

	private[vars] override def double_-(implicit ev :T TypeEquiv Double) :Double = {
		val self = ev(this)
		val res = -self.value; self.value = res; res
	}

}






object Var {

	/** Unbox the value hold by a `Var`. */
	@inline final implicit def unboxVar[@specialized(SpecializedVars) T](variable :Var[T]) :T = variable.get



	/** Create a wrapper over a '''`var`''' of type `T` which can be passed as in/out method parameter. */
	@inline def apply[@specialized(SpecializedVars) T](value :T) :Var[T] = new Var[T](value)

	/** Create a wrapper over a '''`var`''' of type `T` which can be passed as in/out method parameter. */
	@inline def apply[@specialized(SpecializedVars) T](implicit default :DefaultValue[T]) :Var[T] =
		new Var[T](default.value)






	/** Extra implicits which might be helpful but can also lead to tricky bugs. */
	object implicits {
		/** Implicitly creates a `Var` with a given value. This implicit is optional as the main use of `Var[T]`
		  * is to be used as in/out method parameters. In that scenario, using a value identifier instead of a `Var[T]`
		  * makes no sense and would likely be an error.
		  */
		@inline implicit def boxVar[@specialized(SpecializedVars) T](value :T) :Var[T] = new Var[T](value)


		/** Implicit extension of values of any type allowing chained assignments to compatible variables in the form of
		  * `x1 =: x2 =: x3 =: newValue` or `(x1, x2, x3) =: newValue`. This conversion needs to be manually imported
		  * as the wide scope of accepted input values can easily lead to conflicts with different libraries.
		  * @param value value to assign to a chain of variables.
		  */
		implicit class VarMultiAssignment[T](private val value :T) extends AnyVal {

			/** Assign the right-hand value (this) to the left-hand variable, returning this value.
			  * Allows for C-like chained assignments: `x1 =: x2 =: x3 =: 0`.
			  * @param v variable which should be assigned the new value
			  * @return assigned value
			  */
			@inline def =:(v :Var[T]) :T = { v := value; value }

			/** Assigns this (right-hand) value to multiple variables given on the left-hand side of the operator.
			  * `(x1, x2, x3) =: 0`. This is an arguably more readable alternative to chaining individual assignments,
			  * although slightly less efficient.
			  */
			@inline def =:(v1 :Var[T], v2 :Var[T], vs :Var[T]*) :T = {
				v1 := value; v2 := value
				vs match {
					case list :List[T] =>
						var l :List[T] = list
						while (l.nonEmpty) {
							l.head := value; l = l.tail
						}
					case _ => vs foreach { _ := value }
				}
				value
			}
		}


	}






	/** Implicit conversion of a `Var[Boolean]` variable providing basic logical operations. */
	implicit class VarBooleanOps(private val x :Var[Boolean]) extends AnyVal {
		/** Assigns this variable its (eager) logical conjunction with the given argument: `x := x & other`. */
		@inline def &=(other :Boolean) :Unit =  x.value = x.value & other

		/** Assigns this variable its logical conjunction with the given argument: `x := x && other`. */
		@inline def &&=(other: =>Boolean) :Unit = x.value = x.value && other

		/** assigns this variable its (eager) logical disjunction with the given argument: `x := x | other`. */
		@inline def |=(other :Boolean) :Unit = x.value = x.value | other

		/** Assigns this variable its logical disjunction with the given argument: `x := x || other`. */
		@inline def ||=(other: =>Boolean) :Unit =  x.value = x.value || other

		/** Performs logical ''xor'' on this variable with the given argument: `x := x ^ other`. */
		@inline def ^=(other :Boolean) :Unit = x.value = x.value ^ other 

		/** Negates this boolean variable, assigning it the opposite of the current value.
		  * @return the updated value of this variable (after negation). 
		  */
		@inline def neg() :Boolean = { x := !x.value; x.value } 

		/** Negates this boolean variable, assigning it the opposite of the current value. */
		@inline def flip() :Unit = x.value = !x.value 
	}



	
	/** Implicit conversion of a `Var[Int]` variable providing basic arithmetic and bitwise operations. */
	implicit class VarIntArithmetic(private val x :Var[Int]) extends AnyVal {
		/** Increases the value of this variable by the specified number. */
		@inline def +=(n :Int) :Unit = x.value = x.value + n

		/** Decreases the value of this variable by the specified number. */
		@inline def -=(n :Int) :Unit = x.value = x.value - n

		/** Multiplies the value of this variable by the specified number. */
		@inline def *=(n :Int) :Unit = x.value = x.value * n

		/** Divides the value of this variable by the specified number. */
		@inline def /=(n :Int) :Unit = x.value = x.value / n

		/** Assigns to this variable the reminder of dividing it by the specified number. */
		@inline def %=(n :Int) :Unit = x.value = x.value % n

		/** Increments this variable by `1`, C-style. */
		@inline def ++ :Unit = x.value = x.value + 1

		/** Decrements this variable by `1`, C-style. */
		@inline def -- :Unit = x.value = x.value - 1


		/** Increases the value of this variable by the specified number, returning the updated value. */
		@inline def inc(n :Int) :Int = { val res = x.value + n; x.value = res; res }

		/** Decreases the value of this variable by the specified number, returning the updated value. */
		@inline def dec(n :Int) :Int = { val res = x.value - n; x.value = res; res }

		/** Multiplies the value of this variable by the specified number, returning the updated value. */
		@inline def mult(n :Int) :Int = { val res = x.value * n; x.value = res; res }

		/** Divides the value of this variable by the specified number, returning the updated value. */
		@inline def div(n :Int) :Int = { val res = x.value / n; x.value = res; res }

		/** Assigns to this variable the reminder of dividing it by the specified number, returning the updated value. */
		@inline def rem(n :Int) :Int = { val res = x.value % n; x.value = res; res }

		/** Increments this variable by `1`, C-style, returning the updated value. */
		@inline def inc() :Int = { val res = x.value + 1; x.value = res; res }

		/** Decrements this variable by `1`, C-style, returning the updated value. */
		@inline def dec() :Int = { val res = x.value - 1; x.value = res; res }

		/** Atomically sets this variable to its opposite value, returning the updated value (with new sign) */
		@inline def neg() :Int = { val res = -x.value; x.value = res; res }


		/** Performs bitwise disjunction on this variable with the given number. */
		@inline def |=(n :Int) :Unit = x.value = x.value | n

		/** Performs bitwise conjunction on this variable with the given number. */
		@inline def &=(n :Int) :Unit = x.value = x.value & n

		/** Performs bitwise exclusive disjunction on this variable with the given number. */
		@inline def ^=(n :Int) :Unit = x.value = x.value ^ n

		/** Bit-shifts right the value of this variable by the specified number of bits, replacing shifted higher bits with the sign bit. */
		@inline def >>=(n :Int) :Unit = x.value = x.value >> n

		/** Bit-shifts right the value of this variable by the specified number of bits, replacing shifted higher bits with zeros. */
		@inline def >>>=(n :Int) :Unit = x.value = x.value >>> n

		/** Bit-shifts left the value of this variable by the specified number of bits, replacing shifted lower bits with zeros. */
		@inline def <<=(n :Int) :Unit = x.value = x.value << n

		/** Assigns this variable its bitwise negation: `this := !this.value`. */
		@inline def flip() :Unit = x := ~x.value
	}




	/** Implicit conversion of a `Var[Long]` variable providing basic arithmetic and bitwise operations. */
	implicit class VarLongArithmetic(private val x :Var[Long]) extends AnyVal {
		
		/** Increases the value of this variable by the specified number. */
		@inline def +=(n :Long) :Unit = x.value = x.value + n

		/** Decreases the value of this variable by the specified number. */
		@inline def -=(n :Long) :Unit = x.value = x.value - n

		/** Multiplies the value of this variable by the specified number. */
		@inline def *=(n :Long) :Unit = x.value = x.value * n

		/** Divides the value of this variable by the specified number. */
		@inline def /=(n :Long) :Unit = x.value = x.value / n

		/** Assigns to this variable the reminder of dividing it by the specified number. */
		@inline def %=(n :Long) :Unit = x.value = x.value % n

		/** Increments this variable by `1`, C-style. */
		@inline def ++ :Unit = x.value = x.value + 1

		/** Decrements this variable by `1`, C-style. */
		@inline def -- :Unit = x.value = x.value - 1


		/** Increases the value of this variable by the specified number, returning the updated value. */
		@inline def inc(n :Long) :Long = { val res = x.value + n; x.value = res; res }

		/** Decreases the value of this variable by the specified number, returning the updated value. */
		@inline def dec(n :Long) :Long = { val res = x.value - n; x.value = res; res }

		/** Multiplies the value of this variable by the specified number, returning the updated value. */
		@inline def mult(n :Long) :Long = { val res = x.value * n; x.value = res; res }

		/** Divides the value of this variable by the specified number, returning the updated value. */
		@inline def div(n :Long) :Long = { val res = x.value / n; x.value = res; res }

		/** Assigns to this variable the reminder of dividing it by the specified number, returning the updated value. */
		@inline def rem(n :Long) :Long = { val res = x.value % n; x.value = res; res }

		/** Increments this variable by `1`, C-style, returning the updated value. */
		@inline def inc() :Long = { val res = x.value + 1; x.value = res; res }

		/** Decrements this variable by `1`, C-style, returning the updated value. */
		@inline def dec() :Long = { val res = x.value - 1; x.value = res; res }

		/** Atomically sets this variable to its opposite value, returning the updated value (with new sign) */
		@inline def neg() :Long = { val res = -x.value; x.value = res; res }


		/** Performs bitwise disjunction on this variable with the given number. */
		@inline def |=(n :Long) :Unit = x.value = x.value | n

		/** Performs bitwise conjunction on this variable with the given number. */
		@inline def &=(n :Long) :Unit = x.value = x.value & n

		/** Performs bitwise exclusive disjunction on this variable with the given number. */
		@inline def ^=(n :Long) :Unit = x.value = x.value ^ n

		/** Bit-shifts right the value of this variable by the specified number of bits, replacing shifted higher bits with the sign bit. */
		@inline def >>=(n :Long) :Unit = x.value = x.value >> n

		/** Bit-shifts right the value of this variable by the specified number of bits, replacing shifted higher bits with zeros. */
		@inline def >>>=(n :Long) :Unit = x.value = x.value >>> n

		/** Bit-shifts left the value of this variable by the specified number of bits, replacing shifted lower bits with zeros. */
		@inline def <<=(n :Long) :Unit = x.value = x.value << n

		/** Assigns this variable its bitwise negation: `this := !this.value`. */
		@inline def flip() :Unit = x.value = ~x.value
	}




	/** Implicit conversion of a `Var[Float]` variable providing basic arithmetic operations. */
	implicit class VarFloatArithmetic(private val x :Var[Float]) extends AnyVal {
		/** Increases the value of this variable by the specified number. */
		@inline def +=(n :Float) :Unit = x.value = x.value + n

		/** Decreases the value of this variable by the specified number. */
		@inline def -=(n :Float) :Unit = x.value = x.value - n

		/** Multiplies the value of this variable by the specified number. */
		@inline def *=(n :Float) :Unit = x.value = x.value * n

		/** Divides the value of this variable by the specified number. */
		@inline def /=(n :Float) :Unit = x.value = x.value / n


		/** Increases the value of this variable by the specified number, returning the updated value. */
		@inline def inc(n :Float) :Float = { val res = x.value + n; x.value = res; res }

		/** Decreases the value of this variable by the specified number, returning the updated value. */
		@inline def dec(n :Float) :Float = { val res = x.value - n; x.value = res; res }

		/** Multiplies the value of this variable by the specified number, returning the updated value. */
		@inline def mult(n :Float) :Float = { val res = x.value * n; x.value = res; res }

		/** Divides the value of this variable by the specified number, returning the updated value. */
		@inline def div(n :Float) :Float = { val res = x.value / n; x.value = res; res }


		/** Atomically sets this variable to its opposite value, returning the updated value (with new sign) */
		@inline def neg() :Float = { val res = -x.value; x.value = res; res }
	}




	/** Implicit conversion of a `Var[Double]` variable providing basic arithmetic operations. */
	implicit class VarDoubleArithmetic(private val x :Var[Double]) extends AnyVal {
		/** Increases the value of this variable by the specified number. */
		@inline def +=(n :Double) :Unit = x.value = x.value + n

		/** Decreases the value of this variable by the specified number. */
		@inline def -=(n :Double) :Unit = x.value = x.value - n

		/** Multiplies the value of this variable by the specified number. */
		@inline def *=(n :Double) :Unit = x.value = x.value * n

		/** Divides the value of this variable by the specified number. */
		@inline def /=(n :Double) :Unit = x.value = x.value / n


		/** Increases the value of this variable by the specified number, returning the updated value. */
		@inline def inc(n :Double) :Double = { val res = x.value + n; x.value = res; res }

		/** Decreases the value of this variable by the specified number, returning the updated value. */
		@inline def dec(n :Double) :Double = { val res = x.value - n; x.value = res; res }

		/** Multiplies the value of this variable by the specified number, returning the updated value. */
		@inline def mult(n :Double) :Double = { val res = x.value * n; x.value = res; res }

		/** Divides the value of this variable by the specified number, returning the updated value. */
		@inline def div(n :Double) :Double = { val res = x.value / n; x.value = res; res }

		/** Atomically sets this variable to its opposite value, returning the updated value (with new sign) */
		@inline def neg() :Double = { val res = -x.value; x.value = res; res }
	}
	

}

