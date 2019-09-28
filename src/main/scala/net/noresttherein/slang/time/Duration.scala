package net.noresttherein.slang.time

import java.{time => j}
import java.util.concurrent.{TimeUnit => JTimeUnit}

import scala.concurrent.{duration => s}







class Duration(override val toJava: j.Duration) extends AnyVal with FiniteTimeSpan with Serializable {
	override def inNanos :Long = toJava.toNanos

	@inline override def inMicros :Long = {
		val seconds = toJava.getSeconds
		val max = Long.MaxValue / 1000000L
		if (seconds == 0L)
			toJava.getNano / 1000L
		else if (seconds > max || seconds < -max)
			overflow(toString, "inMicros")
		else
			seconds * 1000000L + toJava.getNano / 1000L
	}
	@inline override def toMicros :Double = toJava.getSeconds * 1000000d + toJava.getNano / 1000d

	@inline override def inMillis :Long = toJava.toMillis
	@inline override def toMillis :Double = toJava.getSeconds * 1000d + toJava.getNano.toDouble * NanosInMilli
	@inline override def asMillis :Milliseconds = new Milliseconds(toJava.toMillis)

	@inline override def inSeconds :Long = toJava.toSeconds
	@inline override def toSeconds :Double = toJava.toSeconds + toJava.toNanos / NanosInSecond.toDouble
	@inline override def inMinutes :Long = toJava.toMinutes
	@inline override def toMinutes :Double = toJava.toSeconds / 60d + toJava.toNanos / NanosInMinute.toDouble
	@inline override def inHours :Long = toJava.toHours
	@inline override def toHours :Double = toJava.toSeconds / 3600d + toJava.toNanos / NanosInHour.toDouble
	@inline override def inDays :Long  = toJava.toDays
	@inline override def toDays :Double = toJava.toSeconds / 86400d + toJava.toNanos / NanosInDay.toDouble

	override def in(unit :TimeUnit) :Long = {
		val nanoLen = unit.inNanos
		val seconds = toJava.getSeconds; val nanos = toJava.getNano
		if (nanoLen < NanosInSecond) {
			val multiplier = NanosInSecond / nanoLen
			val nanoPart = nanos / nanoLen
			val max = (Long.MaxValue - nanoPart) / multiplier
			if (seconds > max || seconds < -max)
				overflow(toString," in ", unit.toString)
			else
				seconds * multiplier + nanoPart
		} else
			seconds / (nanoLen / NanosInSecond)
	}

	@inline override def to(unit :TimeUnit) :Double = {
		val nanoLen = unit.inNanos
		if (nanoLen < NanosInSecond)
			toJava.getSeconds.toDouble * (NanosInSecond / nanoLen) + toJava.getNano.toDouble / nanoLen
		else
			toJava.getSeconds.toDouble / (nanoLen / NanosInSecond) + toJava.getNano.toDouble / nanoLen
	}

	@inline override def nanos :Int = toJava.toNanosPart
	@inline override def seconds :Int = toJava.toSecondsPart
	@inline override def minutes :Int = toJava.toNanosPart
	@inline override def hours :Long = toJava.toHours

	override def unit :TimeUnit = (toJava.toSeconds, toJava.getNano) match {
		case (s, 0) =>
			if (s % NanosInMinute == s)
				if (s % NanosInHour == s) DateTimeUnit.Hours
				else DateTimeUnit.Minutes
			else DateTimeUnit.Seconds
		case (_, n) =>
			if (n % NanosInMicro == n)
				if (n % NanosInMilli == n) DateTimeUnit.Millis
				else DateTimeUnit.Micros
			else DateTimeUnit.Nanos
	}



	@inline override def toDuration :Duration = this

	@inline override def toScala :s.Duration =
		s.Duration(toJava.getSeconds * NanosInSecond + toJava.getNano, JTimeUnit.NANOSECONDS)

	@inline override def isZero :Boolean = toJava.isZero

	@inline override def signum :Int = {
		val seconds = toJava.getSeconds
		if (seconds > 0) 1
		else if (seconds < 0) -1
		else java.lang.Integer.signum(toJava.getNano)
	}

	@inline override def abs :Duration =
		if (signum >= 0) this
		else {
			val seconds = toJava.getSeconds
			if (seconds == Long.MinValue)
				overflow(toString, "abs")
			new Duration(j.Duration.ofSeconds(-toJava.getSeconds, -toJava.getNano))
		}

	@inline override def unary_- :Duration = {
		val seconds = toJava.getSeconds
		val nanos = toJava.getNano
		if (seconds == 0L && nanos == 0)
			this
		else if (seconds == Long.MinValue)
			overflow(toString, "-")
		else
			new Duration(j.Duration.ofSeconds(-seconds, -nanos))
	}



	@inline override def +(time :TimeSpan) :TimeSpan = {
		val s = toJava.getSeconds; val n = toJava.getNano
		if (s == 0 && n == 0) time
		else if (time.signum == 0) this
		else time.add(s, n)
	}

	@inline override def +(time :FiniteTimeSpan) :FiniteTimeSpan = time.add(time.inSeconds, time.nanos)

	@inline def +(time :Duration) :Duration = new Duration(toJava plus time.toJava)

	override def add(seconds :Long, nanos :Int) :Duration = {
		val s = toJava.getSeconds
		if (if (s > 0) seconds > Long.MaxValue - s else s < Long.MinValue - s)
			overflow(s"(${seconds}s ${nanos}ns)", " + ", toString)
		new Duration(j.Duration.ofSeconds(s + seconds, this.nanos + nanos))
	}



	@inline override def -(time :TimeSpan) :TimeSpan =
		if (time.signum == 0) this
		else time.subtractFrom(toJava.getSeconds, toJava.getNano)

	@inline override def -(time :FiniteTimeSpan) :FiniteTimeSpan = time.subtractFrom(toJava.getSeconds, toJava.getNano)

	@inline def -(time :Duration) :Duration = new Duration(toJava minus time.toJava)

	override def subtractFrom(seconds :Long, nanos :Int) :Duration = {
		val s = toJava.getSeconds
		if (if (s > 0) seconds < Long.MinValue + s else seconds > Long.MaxValue + s)
			overflow(s"(${seconds}s ${nanos}ns)", " - ", toString)
		new Duration(j.Duration.ofSeconds(seconds -s, nanos - this.nanos))
	}



	@inline override def /(time :TimeSpan) :Double =
		if (time.isInfinite) time.signum.toDouble
		else divideBy(time.inSeconds, time.nanos)

	@inline def /(time :Duration) :Double = divideBy(time.getSeconds, time.getNano)

	@inline def /%(time :Duration) :Long = toJava dividedBy time.toJava

	private[slang] def divideBy(seconds :Long, nano :Int) :Double = {
		val s1 = toJava.getSeconds;	val n1 = toJava.getNano
		if (seconds == 0 && nano == 0)
			throw new ArithmeticException(s"($this) / 0")
		else if (s1 == 0 && n1 == 0)
			0d
		else
			((BigDecimal(s1) * NanosInSecond + n1) / (BigDecimal(seconds) * NanosInSecond + nano)).toDouble
	}


	@inline override def /(d :Long) :Duration = new Duration(toJava dividedBy d)

	override def /(d :Double) :Duration =
		if (d == 0d) throw new ArithmeticException(s"($this) / 0")
		else {
			val length = (BigDecimal(toJava.getSeconds) * NanosInSecond + toJava.getNano) / d
			val seconds = length / NanosInSecond
			if (!seconds.isValidLong)
				throw new ArithmeticException(s"Long overflow: $this / $d")
			new Duration(j.Duration.ofSeconds(seconds.toLong, (length % NanosInSecond).toInt))
		}



	@inline override def *(d :Long) :Duration =
		if (d == 0) Duration.Zero
		else new Duration(toJava.multipliedBy(d))

	@inline override def *(d :Double) :Duration =
		if (d == 0d) Duration.Zero
		else (toJava.getSeconds, toJava.getNano) match {
			case (0L, 0) => this
			case (s, n) => new Duration(j.Duration.ofSeconds((s / d).toLong, (n / d).toInt))
		}

	@inline override def /(unit :TimeUnit) :Double = {
		val len = unit.inNanos
		divideBy(len / NanosInSecond, (len % NanosInSecond).toInt)
	}

	override def %(unit :TimeUnit) :Duration = {
		val duration = unit.inNanos
		if (duration <= NanosInSecond)
			new Duration(j.Duration.ofSeconds(0, toJava.getNano % duration))
		else
			new Duration(j.Duration.ofSeconds(toJava.getSeconds % (duration / NanosInSecond), toJava.getNano))
	}


	@inline def compare(that :Duration) :Int = toJava compareTo that.toJava

	@inline def <=(that :Duration) :Boolean = {
		val s1 = toJava.getSeconds; val s2 = that.toJava.getSeconds
		s1 < s2 || s1 == s2 && toJava.getNano <= that.toJava.getNano
	}
	@inline def < (that :Duration) :Boolean = !(this >= that)

	@inline def >=(that :Duration) :Boolean = {
		val s1 = toJava.getSeconds; val s2 = that.toJava.getSeconds
		s1 > s2 || s1 == s2 && toJava.getNano >= that.toJava.getNano
	}
	@inline def > (that :Duration) :Boolean = !(this >= that)

	@inline def min(that :Duration) :Duration = if (this <= that) this else that
	@inline def max(that :Duration) :Duration = if (this >= that) this else that

	@inline def ===(that :Duration) :Boolean = toJava == that.toJava

}






object Duration {

	@inline def apply(duration :j.Duration) :Duration = new Duration(duration)

	@inline def apply(length :Long, unit :TimeUnit) :Duration = j.Duration.of(length, unit.toJava)

	@inline def apply(seconds :Long) :Duration = new Duration(j.Duration.ofSeconds(seconds))

	@inline def apply(seconds :Long, nanos :Int) :Duration = new Duration(j.Duration.ofSeconds(seconds, nanos))


	@inline def unapply(span :TimeSpan) :Option[(Long, Int)] = span match {
		case d :Duration => Some(d.toJava.getSeconds, d.toJava.getNano)
		case _ => None
	}


	@inline def between(from :Timestamp, until :Timestamp) :Duration =
		new Duration(j.Duration.between(from.toJava, until.toJava))

	@inline def between(from :DefiniteTime, until :DefiniteTime) :Duration =
		new Duration(j.Duration.between(from.toInstant, until.toInstant))

	@inline def between(from :TimeOfDay, until :TimeOfDay) :Duration =
		new Duration(j.Duration.between(from.toJava, until.toJava))

	@inline def between(from :DateTime, until :DateTime) :Duration =
		new Duration(j.Duration.between(from.toJava, until.toJava))



	@inline def since(moment :DefiniteTime)(implicit time :Time = Time.Local) :Duration =
		new Duration(j.Duration.between(moment.toInstant, time.clock.instant))

	@inline def until(moment :DefiniteTime)(implicit time :Time = Time.Local) :Duration =
		new Duration(j.Duration.between(time.clock.instant, moment.toInstant))

	final val Zero = new Duration(j.Duration.ZERO)
	final val Max = new Duration(j.Duration.ofSeconds(Long.MaxValue, Int.MaxValue))
	final val Min = new Duration(j.Duration.ofSeconds(Long.MinValue, 0))
}


