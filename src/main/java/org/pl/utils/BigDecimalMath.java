package org.pl.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.security.ProviderException;


/**
 * BigDecimal special functions.
 * <a href="http://arxiv.org/abs/0908.3030">A Java Math.BigDecimal Implementation of Core Mathematical Functions</a>
 *
 * @author Richard J. Mathar
 * <a href="http://apfloat.org/">apfloat</a>
 * <a href="http://dfp.sourceforge.net/">dfp</a>
 * <a href="http://jscience.org/">JScience</a>
 * @since 2009-05-22
 */
public class BigDecimalMath {

    /**
     * The base of the natural logarithm in a predefined accuracy.
     * http://www.cs.arizona.edu/icon/oddsends/e.htm
     * The precision of the predefined constant is one less than
     * the string's length, taking into account the decimal dot.
     * static int E_PRECISION = E.length()-1 ;
     */
    static BigDecimal E = new BigDecimal("2.71828182845904523536028747135266249775724709369995957496696762772407663035354" +
            "759457138217852516642742746639193200305992181741359662904357290033429526059563" +
            "073813232862794349076323382988075319525101901157383418793070215408914993488416" +
            "750924476146066808226480016847741185374234544243710753907774499206955170276183" +
            "860626133138458300075204493382656029760673711320070932870912744374704723069697" +
            "720931014169283681902551510865746377211125238978442505695369677078544996996794" +
            "686445490598793163688923009879312773617821542499922957635148220826989519366803" +
            "318252886939849646510582093923982948879332036250944311730123819706841614039701" +
            "983767932068328237646480429531180232878250981945581530175671736133206981125099" +
            "618188159304169035159888851934580727386673858942287922849989208680582574927961" +
            "048419844436346324496848756023362482704197862320900216099023530436994184914631" +
            "409343173814364054625315209618369088870701676839642437814059271456354906130310" +
            "720851038375051011574770417189861068739696552126715468895703503540212340784981" +
            "933432106817012100562788023519303322474501585390473041995777709350366041699732" +
            "972508868769664035557071622684471625607988265178713419512466520103059212366771" +
            "943252786753985589448969709640975459185695638023637016211204774272283648961342" +
            "251644507818244235294863637214174023889344124796357437026375529444833799801612" +
            "549227850925778256209262264832627793338656648162772516401910590049164499828931");

    /**
     * Euler's constant Pi.
     * http://www.cs.arizona.edu/icon/oddsends/pi.htm
     */
    static BigDecimal PI = new BigDecimal("3.14159265358979323846264338327950288419716939937510582097494459230781640628620" +
            "899862803482534211706798214808651328230664709384460955058223172535940812848111" +
            "745028410270193852110555964462294895493038196442881097566593344612847564823378" +
            "678316527120190914564856692346034861045432664821339360726024914127372458700660" +
            "631558817488152092096282925409171536436789259036001133053054882046652138414695" +
            "194151160943305727036575959195309218611738193261179310511854807446237996274956" +
            "735188575272489122793818301194912983367336244065664308602139494639522473719070" +
            "217986094370277053921717629317675238467481846766940513200056812714526356082778" +
            "577134275778960917363717872146844090122495343014654958537105079227968925892354" +
            "201995611212902196086403441815981362977477130996051870721134999999837297804995" +
            "105973173281609631859502445945534690830264252230825334468503526193118817101000" +
            "313783875288658753320838142061717766914730359825349042875546873115956286388235" +
            "378759375195778185778053217122680661300192787661119590921642019893809525720106" +
            "548586327886593615338182796823030195203530185296899577362259941389124972177528" +
            "347913151557485724245415069595082953311686172785588907509838175463746493931925" +
            "506040092770167113900984882401285836160356370766010471018194295559619894676783" +
            "744944825537977472684710404753464620804668425906949129331367702898915210475216" +
            "205696602405803815019351125338243003558764024749647326391419927260426992279678" +
            "235478163600934172164121992458631503028618297455570674983850549458858692699569" +
            "092721079750930295532116534498720275596023648066549911988183479775356636980742" +
            "654252786255181841757467289097777279380008164706001614524919217321721477235014");

    /**
     * Euler-Mascheroni constant lower-case gamma.
     * http://www.worldwideschool.org/library/books/sci/math/MiscellaneousMathematicalConstants/chap35.html
     */
    static BigDecimal GAMMA = new BigDecimal("0.577215664901532860606512090082402431" +
            "0421593359399235988057672348848677267776646709369470632917467495146314472498070" +
            "8248096050401448654283622417399764492353625350033374293733773767394279259525824" +
            "7094916008735203948165670853233151776611528621199501507984793745085705740029921" +
            "3547861466940296043254215190587755352673313992540129674205137541395491116851028" +
            "0798423487758720503843109399736137255306088933126760017247953783675927135157722" +
            "6102734929139407984301034177717780881549570661075010161916633401522789358679654" +
            "9725203621287922655595366962817638879272680132431010476505963703947394957638906" +
            "5729679296010090151251959509222435014093498712282479497471956469763185066761290" +
            "6381105182419744486783638086174945516989279230187739107294578155431600500218284" +
            "4096053772434203285478367015177394398700302370339518328690001558193988042707411" +
            "5422278197165230110735658339673487176504919418123000406546931429992977795693031" +
            "0050308630341856980323108369164002589297089098548682577736428825395492587362959" +
            "6133298574739302373438847070370284412920166417850248733379080562754998434590761" +
            "6431671031467107223700218107450444186647591348036690255324586254422253451813879" +
            "1243457350136129778227828814894590986384600629316947188714958752549236649352047" +
            "3243641097268276160877595088095126208404544477992299157248292516251278427659657" +
            "0832146102982146179519579590959227042089896279712553632179488737642106606070659" +
            "8256199010288075612519913751167821764361905705844078357350158005607745793421314" +
            "49885007864151716151945");

    /**
     * Natural logarithm of 2.
     * http://www.worldwideschool.org/library/books/sci/math/MiscellaneousMathematicalConstants/chap58.html
     */
    static BigDecimal LOG2 = new BigDecimal("0.693147180559945309417232121458176568075" +
            "50013436025525412068000949339362196969471560586332699641868754200148102057068573" +
            "368552023575813055703267075163507596193072757082837143519030703862389167347112335" +
            "011536449795523912047517268157493206515552473413952588295045300709532636664265410" +
            "423915781495204374043038550080194417064167151864471283996817178454695702627163106" +
            "454615025720740248163777338963855069526066834113727387372292895649354702576265209" +
            "885969320196505855476470330679365443254763274495125040606943814710468994650622016" +
            "772042452452961268794654619316517468139267250410380254625965686914419287160829380" +
            "317271436778265487756648508567407764845146443994046142260319309673540257444607030" +
            "809608504748663852313818167675143866747664789088143714198549423151997354880375165" +
            "861275352916610007105355824987941472950929311389715599820565439287170007218085761" +
            "025236889213244971389320378439353088774825970171559107088236836275898425891853530" +
            "243634214367061189236789192372314672321720534016492568727477823445353476481149418" +
            "642386776774406069562657379600867076257199184734022651462837904883062033061144630" +
            "073719489002743643965002580936519443041191150608094879306786515887090060520346842" +
            "973619384128965255653968602219412292420757432175748909770675268711581705113700915" +
            "894266547859596489065305846025866838294002283300538207400567705304678700184162404" +
            "418833232798386349001563121889560650553151272199398332030751408426091479001265168" +
            "243443893572472788205486271552741877243002489794540196187233980860831664811490930" +
            "667519339312890431641370681397776498176974868903887789991296503619270710889264105" +
            "230924783917373501229842420499568935992206602204654941510613");


    /**
     * The square root.
     *
     * @param x  the non-negative argument.
     * @param mc The required mathematical precision.
     * @return the square root of the BigDecimal.
     * @author Richard J. Mathar
     * @since 2008-10-27
     */
    static public BigDecimal sqrt(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("negative argument " + x.toString() + " of square root");
        if (x.abs().subtract(new BigDecimal(Math.pow(10., -mc.getPrecision()))).compareTo(BigDecimal.ZERO) < 0)
            return BigDecimalMath.scalePrec(BigDecimal.ZERO, mc);
        /* start the computation from a double precision estimate */
        BigDecimal s = new BigDecimal(Math.sqrt(x.doubleValue()), mc);
        final BigDecimal half = new BigDecimal("2");

        /* increase the local accuracy by 2 digits */
        MathContext locmc = new MathContext(mc.getPrecision() + 2, mc.getRoundingMode());

        /* relative accuracy requested is 10^(-precision)
         */
        final double eps = Math.pow(10.0, -mc.getPrecision());
        for (; ; ) {
            /* s = s -(s/2-x/2s); test correction s-x/s for being
             * smaller than the precision requested. The relative correction is 1-x/s^2,
             * (actually half of this, which we use for a little bit of additional protection).
             */
            if (Math.abs(BigDecimal.ONE.subtract(x.divide(s.pow(2, locmc), locmc)).doubleValue()) < eps)
                break;
            s = s.add(x.divide(s, locmc)).divide(half, locmc);
            /* debugging
             * System.out.println("itr "+x.round(locmc).toString() + " " + s.round(locmc).toString()) ;
             */
        }
        return s;
    } /* BigDecimalMath.sqrt */

    /**
     * The square root.
     *
     * @param x the non-negative argument.
     * @return the square root of the BigDecimal rounded to the precision implied by x.
     * @author Richard J. Mathar
     * @since 2009-06-25
     */
    static public BigDecimal sqrt(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("negative argument " + x.toString() + " of square root");

        return root(2, x);
    } /* BigDecimalMath.sqrt */

    /**
     * The cube root.
     *
     * @param x The argument.
     * @return The cubic root of the BigDecimal rounded to the precision implied by x.
     * The sign of the result is the sign of the argument.
     * @author Richard J. Mathar
     * @since 2009-08-16
     */
    static public BigDecimal cbrt(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return root(3, x.negate()).negate();
        else
            return root(3, x);
    } /* BigDecimalMath.cbrt */

    /**
     * The integer root.
     *
     * @param n the positive argument.
     * @param x the non-negative argument.
     * @return The n-th root of the BigDecimal rounded to the precision implied by x, x^(1/n).
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal root(final int n, final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("negative argument " + x.toString() + " of root");
        if (n <= 0)
            throw new ArithmeticException("negative power " + n + " of root");

        if (n == 1)
            return x;

        /* start the computation from a double precision estimate */
        BigDecimal s = new BigDecimal(Math.pow(x.doubleValue(), 1.0 / n));

        /* this creates nth with nominal precision of 1 digit
         */
        final BigDecimal nth = new BigDecimal(n);

        /* Specify an internal accuracy within the loop which is
         * slightly larger than what is demanded by 'eps' below.
         */
        final BigDecimal xhighpr = scalePrec(x, 2);
        MathContext mc = new MathContext(2 + x.precision());

        /* Relative accuracy of the result is eps.
         */
        final double eps = x.ulp().doubleValue() / (2 * n * x.doubleValue());
        for (; ; ) {
            /* s = s -(s/n-x/n/s^(n-1)) = s-(s-x/s^(n-1))/n; test correction s/n-x/s for being
             * smaller than the precision requested. The relative correction is (1-x/s^n)/n,
             */
            BigDecimal c = xhighpr.divide(s.pow(n - 1), mc);
            c = s.subtract(c);
            MathContext locmc = new MathContext(c.precision());
            c = c.divide(nth, locmc);
            s = s.subtract(c);
            if (Math.abs(c.doubleValue() / s.doubleValue()) < eps)
                break;
        }
        return s.round(new MathContext(err2prec(eps)));
    } /* BigDecimalMath.root */

    /**
     * The hypotenuse.
     *
     * @param x the first argument.
     * @param y the second argument.
     * @return the square root of the sum of the squares of the two arguments, sqrt(x^2+y^2).
     * @author Richard J. Mathar
     * @since 2009-06-25
     */
    static public BigDecimal hypot(final BigDecimal x, final BigDecimal y) {
        /* compute x^2+y^2
         */
        BigDecimal z = x.pow(2).add(y.pow(2));

        /* truncate to the precision set by x and y. Absolute error = 2*x*xerr+2*y*yerr,
         * where the two errors are 1/2 of the ulp's.  Two intermediate protectio digits.
         */
        BigDecimal zerr = x.abs().multiply(x.ulp()).add(y.abs().multiply(y.ulp()));
        MathContext mc = new MathContext(2 + err2prec(z, zerr));

        /* Pull square root */
        z = sqrt(z.round(mc));

        /* Final rounding. Absolute error in the square root is (y*yerr+x*xerr)/z, where zerr holds 2*(x*xerr+y*yerr).
         */
        mc = new MathContext(err2prec(z.doubleValue(), 0.5 * zerr.doubleValue() / z.doubleValue()));
        return z.round(mc);
    } /* BigDecimalMath.hypot */

    /**
     * The hypotenuse.
     *
     * @param n the first argument.
     * @param x the second argument.
     * @return the square root of the sum of the squares of the two arguments, sqrt(n^2+x^2).
     * @author Richard J. Mathar
     * @since 2009-08-05
     */
    static public BigDecimal hypot(final int n, final BigDecimal x) {
        /* compute n^2+x^2 in infinite precision
         */
        BigDecimal z = (new BigDecimal(n)).pow(2).add(x.pow(2));

        /* Truncate to the precision set by x. Absolute error = in z (square of the result) is |2*x*xerr|,
         * where the error is 1/2 of the ulp. Two intermediate protection digits.
         * zerr is a signed value, but used only in conjunction with err2prec(), so this feature does not harm.
         */
        double zerr = x.doubleValue() * x.ulp().doubleValue();
        MathContext mc = new MathContext(2 + err2prec(z.doubleValue(), zerr));

        /* Pull square root */
        z = sqrt(z.round(mc));

        /* Final rounding. Absolute error in the square root is x*xerr/z, where zerr holds 2*x*xerr.
         */
        mc = new MathContext(err2prec(z.doubleValue(), 0.5 * zerr / z.doubleValue()));
        return z.round(mc);
    } /* BigDecimalMath.hypot */


    /**
     * A suggestion for the maximum numter of terms in the Taylor expansion of the exponential.
     */
    static private int TAYLOR_NTERM = 8;

    /**
     * The exponential function.
     *
     * @param x the argument.
     * @return exp(x).
     * The precision of the result is implicitly defined by the precision in the argument.
     * In particular this means that "Invalid Operation" errors are thrown if catastrophic
     * cancellation of digits causes the result to have no valid digits left.
     * @author Richard J. Mathar
     * @since 2009-05-29
     */
    static public BigDecimal exp(BigDecimal x) {
        /* To calculate the value if x is negative, use exp(-x) = 1/exp(x)
         */
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            final BigDecimal invx = exp(x.negate());
            /* Relative error in inverse of invx is the same as the relative errror in invx.
             * This is used to define the precision of the result.
             */
            MathContext mc = new MathContext(invx.precision());
            return BigDecimal.ONE.divide(invx, mc);
        } else if (x.compareTo(BigDecimal.ZERO) == 0) {
            /* recover the valid number of digits from x.ulp(), if x hits the
             * zero. The x.precision() is 1 then, and does not provide this information.
             */
            return scalePrec(BigDecimal.ONE, -(int) (Math.log10(x.ulp().doubleValue())));
        } else {
            /* Push the number in the Taylor expansion down to a small
             * value where TAYLOR_NTERM terms will do. If x<1, the n-th term is of the order
             * x^n/n!, and equal to both the absolute and relative error of the result
             * since the result is close to 1. The x.ulp() sets the relative and absolute error
             * of the result, as estimated from the first Taylor term.
             * We want x^TAYLOR_NTERM/TAYLOR_NTERM! < x.ulp, which is guaranteed if
             * x^TAYLOR_NTERM < TAYLOR_NTERM*(TAYLOR_NTERM-1)*...*x.ulp.
             */
            final double xDbl = x.doubleValue();
            final double xUlpDbl = x.ulp().doubleValue();
            if (Math.pow(xDbl, TAYLOR_NTERM) < TAYLOR_NTERM * (TAYLOR_NTERM - 1.0) * (TAYLOR_NTERM - 2.0) * xUlpDbl) {
                /* Add TAYLOR_NTERM terms of the Taylor expansion (Euler's sum formula)
                 */
                BigDecimal resul = BigDecimal.ONE;

                /* x^i */
                BigDecimal xpowi = BigDecimal.ONE;

                /* i factorial */
                BigInteger ifac = BigInteger.ONE;

                /* TAYLOR_NTERM terms to be added means we move x.ulp() to the right
                 * for each power of 10 in TAYLOR_NTERM, so the addition won't add noise beyond
                 * what's already in x.
                 */
                MathContext mcTay = new MathContext(err2prec(1., xUlpDbl / TAYLOR_NTERM));
                for (int i = 1; i <= TAYLOR_NTERM; i++) {
                    ifac = ifac.multiply(new BigInteger("" + i));
                    xpowi = xpowi.multiply(x);
                    final BigDecimal c = xpowi.divide(new BigDecimal(ifac), mcTay);
                    resul = resul.add(c);
                    if (Math.abs(xpowi.doubleValue()) < i && Math.abs(c.doubleValue()) < 0.5 * xUlpDbl)
                        break;
                }
                /* exp(x+deltax) = exp(x)(1+deltax) if deltax is <<1. So the relative error
                 * in the result equals the absolute error in the argument.
                 */
                MathContext mc = new MathContext(err2prec(xUlpDbl / 2.));
                return resul.round(mc);
            } else {
                /* Compute exp(x) = (exp(0.1*x))^10. Division by 10 does not lead
                 * to loss of accuracy.
                 */
                int exSc = (int) (1.0 - Math.log10(TAYLOR_NTERM * (TAYLOR_NTERM - 1.0) * (TAYLOR_NTERM - 2.0) * xUlpDbl
                        / Math.pow(xDbl, TAYLOR_NTERM)) / (TAYLOR_NTERM - 1.0));
                BigDecimal xby10 = x.scaleByPowerOfTen(-exSc);
                BigDecimal expxby10 = exp(xby10);

                /* Final powering by 10 means that the relative error of the result
                 * is 10 times the relative error of the base (First order binomial expansion).
                 * This looses one digit.
                 */
                MathContext mc = new MathContext(expxby10.precision() - exSc);
                /* Rescaling the powers of 10 is done in chunks of a maximum of 8 to avoid an invalid operation
                 * response by the BigDecimal.pow library or integer overflow.
                 */
                while (exSc > 0) {
                    int exsub = Math.min(8, exSc);
                    exSc -= exsub;
                    MathContext mctmp = new MathContext(expxby10.precision() - exsub + 2);
                    int pex = 1;
                    while (exsub-- > 0)
                        pex *= 10;
                    expxby10 = expxby10.pow(pex, mctmp);
                }
                return expxby10.round(mc);
            }
        }
    } /* BigDecimalMath.exp */

    /**
     * The base of the natural logarithm.
     *
     * @param mc the required precision of the result
     * @return exp(1) = 2.71828....
     * @author Richard J. Mathar
     * @since 2009-05-29
     */
    static public BigDecimal exp(final MathContext mc) {
        /* look it up if possible */
        if (mc.getPrecision() < E.precision())
            return E.round(mc);
        else {
            /* Instantiate a 1.0 with the requested pseudo-accuracy
             * and delegate the computation to the public method above.
             */
            BigDecimal uni = scalePrec(BigDecimal.ONE, mc.getPrecision());
            return exp(uni);
        }
    } /* BigDecimalMath.exp */

    /**
     * The natural logarithm.
     *
     * @param x the argument.
     * @return ln(x).
     * The precision of the result is implicitly defined by the precision in the argument.
     * @author Richard J. Mathar
     * @since 2009-05-29
     */
    static public BigDecimal log(BigDecimal x) {
        /* the value is undefined if x is negative.
         */
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Cannot take log of negative " + x.toString());
        else if (x.compareTo(BigDecimal.ONE) == 0) {
            /* log 1. = 0. */
            return scalePrec(BigDecimal.ZERO, x.precision() - 1);
        } else if (Math.abs(x.doubleValue() - 1.0) <= 0.3) {
            /* The standard Taylor series around x=1, z=0, z=x-1. Abramowitz-Stegun 4.124.
             * The absolute error is err(z)/(1+z) = err(x)/x.
             */
            BigDecimal z = scalePrec(x.subtract(BigDecimal.ONE), 2);
            BigDecimal zpown = z;
            double eps = 0.5 * x.ulp().doubleValue() / Math.abs(x.doubleValue());
            BigDecimal resul = z;
            for (int k = 2; ; k++) {
                zpown = multiplyRound(zpown, z);
                BigDecimal c = divideRound(zpown, k);
                if (k % 2 == 0)
                    resul = resul.subtract(c);
                else
                    resul = resul.add(c);
                if (Math.abs(c.doubleValue()) < eps)
                    break;
            }
            MathContext mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return resul.round(mc);
        } else {
            final double xDbl = x.doubleValue();
            final double xUlpDbl = x.ulp().doubleValue();

            /* Map log(x) = log root[r](x)^r = r*log( root[r](x)) with the aim
             * to move roor[r](x) near to 1.2 (that is, below the 0.3 appearing above), where log(1.2) is roughly 0.2.
             */
            int r = (int) (Math.log(xDbl) / 0.2);

            /* Since the actual requirement is a function of the value 0.3 appearing above,
             * we avoid the hypothetical case of endless recurrence by ensuring that r >= 2.
             */
            r = Math.max(2, r);

            /* Compute r-th root with 2 additional digits of precision
             */
            BigDecimal xhighpr = scalePrec(x, 2);
            BigDecimal resul = root(r, xhighpr);
            resul = log(resul).multiply(new BigDecimal(r));

            /* error propagation: log(x+errx) = log(x)+errx/x, so the absolute error
             * in the result equals the relative error in the input, xUlpDbl/xDbl .
             */
            MathContext mc = new MathContext(err2prec(resul.doubleValue(), xUlpDbl / xDbl));
            return resul.round(mc);
        }
    } /* BigDecimalMath.log */


    /**
     * Power function.
     *
     * @param x Base of the power.
     * @param y Exponent of the power.
     * @return x^y.
     * The estimation of the relative error in the result is |log(x)*err(y)|+|y*err(x)/x|
     * @author Richard J. Mathar
     * @since 2009-06-01
     */
    static public BigDecimal pow(final BigDecimal x, final BigDecimal y) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Cannot power negative " + x.toString());
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            /* return x^y = exp(y*log(x)) ;
             */
            BigDecimal logx = log(x);
            BigDecimal ylogx = y.multiply(logx);
            BigDecimal resul = exp(ylogx);

            /* The estimation of the relative error in the result is |log(x)*err(y)|+|y*err(x)/x|
             */
            double errR = Math.abs(logx.doubleValue() * y.ulp().doubleValue() / 2.)
                    + Math.abs(y.doubleValue() * x.ulp().doubleValue() / 2. / x.doubleValue());
            MathContext mcR = new MathContext(err2prec(1.0, errR));
            return resul.round(mcR);
        }
    } /* BigDecimalMath.pow */

    /**
     * Raise to an integer power and round.
     *
     * @param x The base.
     * @param n The exponent.
     * @return x^n.
     * @author Richard J. Mathar
     * @since 2009-08-13
     * @since 2010-05-26 handle also cases where n is less than zero.
     */
    static public BigDecimal powRound(final BigDecimal x, final int n) {
        /** Special cases: x^1=x and x^0 = 1
         */
        if (n == 1)
            return x;
        else if (n == 0)
            return BigDecimal.ONE;
        else {
            /* The relative error in the result is n times the relative error in the input.
             * The estimation is slightly optimistic due to the integer rounding of the logarithm.
             * Since the standard BigDecimal.pow can only handle positive n, we split the algorithm.
             */
            MathContext mc = new MathContext(x.precision() - (int) Math.log10((double) (Math.abs(n))));
            if (n > 0)
                return x.pow(n, mc);
            else
                return BigDecimal.ONE.divide(x.pow(-n), mc);
        }
    } /* BigDecimalMath.powRound */

    /**
     * Trigonometric sine.
     *
     * @param x The argument in radians.
     * @return sin(x) in the range -1 to 1.
     * @author Richard J. Mathar
     * @since 2009-06-01
     */
    static public BigDecimal sin(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return sin(x.negate()).negate();
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            /* reduce modulo 2pi
             */
            BigDecimal res = mod2pi(x);
            double errpi = 0.5 * Math.abs(x.ulp().doubleValue());
            MathContext mc = new MathContext(2 + err2prec(3.14159, errpi));
            BigDecimal p = PI;
            mc = new MathContext(x.precision());
            if (res.compareTo(p) > 0) {
                /* pi<x<=2pi: sin(x)= - sin(x-pi)
                 */
                return sin(subtractRound(res, p)).negate();
            } else if (res.multiply(new BigDecimal("2")).compareTo(p) > 0) {
                /* pi/2<x<=pi: sin(x)= sin(pi-x)
                 */
                return sin(subtractRound(p, res));
            } else {
                /* for the range 0<=x<Pi/2 one could use sin(2x)=2sin(x)cos(x)
                 * to split this further. Here, use the sine up to pi/4 and the cosine higher up.
                 */
                if (res.multiply(new BigDecimal("4")).compareTo(p) > 0) {
                    /* x>pi/4: sin(x) = cos(pi/2-x)
                     */
                    return cos(subtractRound(p.divide(new BigDecimal("2")), res));
                } else {
                    /* Simple Taylor expansion, sum_{i=1..infinity} (-1)^(..)res^(2i+1)/(2i+1)! */
                    BigDecimal resul = res;

                    /* x^i */
                    BigDecimal xpowi = res;

                    /* 2i+1 factorial */
                    BigInteger ifac = BigInteger.ONE;

                    /* The error in the result is set by the error in x itself.
                     */
                    double xUlpDbl = res.ulp().doubleValue();

                    /* The error in the result is set by the error in x itself.
                     * We need at most k terms to squeeze x^(2k+1)/(2k+1)! below this value.
                     * x^(2k+1) < x.ulp; (2k+1)*log10(x) < -x.precision; 2k*log10(x)< -x.precision;
                     * 2k*(-log10(x)) > x.precision; 2k*log10(1/x) > x.precision
                     */
                    int k = (int) (res.precision() / Math.log10(1.0 / res.doubleValue())) / 2;
                    MathContext mcTay = new MathContext(err2prec(res.doubleValue(), xUlpDbl / k));
                    for (int i = 1; ; i++) {
                        /* TBD: at which precision will 2*i or 2*i+1 overflow?
                         */
                        ifac = ifac.multiply(new BigInteger("" + (2 * i)));
                        ifac = ifac.multiply(new BigInteger("" + (2 * i + 1)));
                        xpowi = xpowi.multiply(res).multiply(res).negate();
                        BigDecimal corr = xpowi.divide(new BigDecimal(ifac), mcTay);
                        resul = resul.add(corr);
                        if (corr.abs().doubleValue() < 0.5 * xUlpDbl)
                            break;
                    }
                    /* The error in the result is set by the error in x itself.
                     */
                    mc = new MathContext(res.precision());
                    return resul.round(mc);
                }
            }
        }
    } /* sin */

    /**
     * Trigonometric cosine.
     *
     * @param x The argument in radians.
     * @return cos(x) in the range -1 to 1.
     * @author Richard J. Mathar
     * @since 2009-06-01
     */
    static public BigDecimal cos(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return cos(x.negate());
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ONE;
        else {
            /* reduce modulo 2pi
             */
            BigDecimal res = mod2pi(x);
            double errpi = 0.5 * Math.abs(x.ulp().doubleValue());
            MathContext mc = new MathContext(2 + err2prec(3.14159, errpi));
            BigDecimal p = PI;
            mc = new MathContext(x.precision());
            if (res.compareTo(p) > 0) {
                /* pi<x<=2pi: cos(x)= - cos(x-pi)
                 */
                return cos(subtractRound(res, p)).negate();
            } else if (res.multiply(new BigDecimal("2")).compareTo(p) > 0) {
                /* pi/2<x<=pi: cos(x)= -cos(pi-x)
                 */
                return cos(subtractRound(p, res)).negate();
            } else {
                                /* for the range 0<=x<Pi/2 one could use cos(2x)= 1-2*sin^2(x)
                                * to split this further, or use the cos up to pi/4 and the sine higher up.
                                        throw new ProviderException("Not implemented: cosine ") ;
                                */
                if (res.multiply(new BigDecimal("4")).compareTo(p) > 0) {
                    /* x>pi/4: cos(x) = sin(pi/2-x)
                     */
                    return sin(subtractRound(p.divide(new BigDecimal("2")), res));
                } else {
                    /* Simple Taylor expansion, sum_{i=0..infinity} (-1)^(..)res^(2i)/(2i)! */
                    BigDecimal resul = BigDecimal.ONE;

                    /* x^i */
                    BigDecimal xpowi = BigDecimal.ONE;

                    /* 2i factorial */
                    BigInteger ifac = BigInteger.ONE;

                    /* The absolute error in the result is the error in x^2/2 which is x times the error in x.
                     */
                    double xUlpDbl = 0.5 * res.ulp().doubleValue() * res.doubleValue();

                    /* The error in the result is set by the error in x^2/2 itself, xUlpDbl.
                     * We need at most k terms to push x^(2k+1)/(2k+1)! below this value.
                     * x^(2k) < xUlpDbl; (2k)*log(x) < log(xUlpDbl);
                     */
                    int k = (int) (Math.log(xUlpDbl) / Math.log(res.doubleValue())) / 2;
                    MathContext mcTay = new MathContext(err2prec(1., xUlpDbl / k));
                    for (int i = 1; ; i++) {
                        /* TBD: at which precision will 2*i-1 or 2*i overflow?
                         */
                        ifac = ifac.multiply(new BigInteger("" + (2 * i - 1)));
                        ifac = ifac.multiply(new BigInteger("" + (2 * i)));
                        xpowi = xpowi.multiply(res).multiply(res).negate();
                        BigDecimal corr = xpowi.divide(new BigDecimal(ifac), mcTay);
                        resul = resul.add(corr);
                        if (corr.abs().doubleValue() < 0.5 * xUlpDbl)
                            break;
                    }
                    /* The error in the result is governed by the error in x itself.
                     */
                    mc = new MathContext(err2prec(resul.doubleValue(), xUlpDbl));
                    return resul.round(mc);
                }
            }
        }
    } /* BigDecimalMath.cos */


    /**
     * The inverse trigonometric sine.
     *
     * @param x the argument.
     * @return the arcsin(x) in radians.
     * @author Richard J. Mathar
     */
    static public BigDecimal asin(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ONE) > 0 || x.compareTo(BigDecimal.ONE.negate()) < 0) {
            throw new ArithmeticException("Out of range argument " + x.toString() + " of asin");
        } else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else if (x.compareTo(BigDecimal.ONE) == 0) {
            /* arcsin(1) = pi/2
             */
            double errpi = Math.sqrt(x.ulp().doubleValue());
            MathContext mc = new MathContext(err2prec(3.14159, errpi));
            return PI.divide(new BigDecimal(2));
        } else if (x.compareTo(BigDecimal.ZERO) < 0) {
            return asin(x.negate()).negate();
        } else if (x.doubleValue() > 0.7) {
            final BigDecimal xCompl = BigDecimal.ONE.subtract(x);
            final double xDbl = x.doubleValue();
            final double xUlpDbl = x.ulp().doubleValue() / 2.;
            final double eps = xUlpDbl / 2. / Math.sqrt(1. - Math.pow(xDbl, 2.));

            final BigDecimal xhighpr = scalePrec(xCompl, 3);
            final BigDecimal xhighprV = divideRound(xhighpr, 4);

            BigDecimal resul = BigDecimal.ONE;

            /* x^(2i+1) */
            BigDecimal xpowi = BigDecimal.ONE;

            /* i factorial */
            BigInteger ifacN = BigInteger.ONE;
            BigInteger ifacD = BigInteger.ONE;

            for (int i = 1; ; i++) {
                ifacN = ifacN.multiply(new BigInteger("" + (2 * i - 1)));
                ifacD = ifacD.multiply(new BigInteger("" + i));
                if (i == 1)
                    xpowi = xhighprV;
                else
                    xpowi = multiplyRound(xpowi, xhighprV);
                BigDecimal c = divideRound(multiplyRound(xpowi, ifacN),
                        ifacD.multiply(new BigInteger("" + (2 * i + 1))));
                resul = resul.add(c);
                /* series started 1+x/12+... which yields an estimate of the sum's error
                 */
                if (Math.abs(c.doubleValue()) < xUlpDbl / 120.)
                    break;
            }
            /* sqrt(2*z)*(1+...)
             */
            xpowi = sqrt(xhighpr.multiply(new BigDecimal(2)));
            resul = multiplyRound(xpowi, resul);

            MathContext mc = new MathContext(resul.precision());
            BigDecimal pihalf = PI.divide(new BigDecimal(2));

            mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return pihalf.subtract(resul, mc);
        } else {
            /* absolute error in the result is err(x)/sqrt(1-x^2) to lowest order
             */
            final double xDbl = x.doubleValue();
            final double xUlpDbl = x.ulp().doubleValue() / 2.;
            final double eps = xUlpDbl / 2. / Math.sqrt(1. - Math.pow(xDbl, 2.));

            final BigDecimal xhighpr = scalePrec(x, 2);
            final BigDecimal xhighprSq = multiplyRound(xhighpr, xhighpr);

            BigDecimal resul = xhighpr.plus();

            /* x^(2i+1) */
            BigDecimal xpowi = xhighpr;

            /* i factorial */
            BigInteger ifacN = BigInteger.ONE;
            BigInteger ifacD = BigInteger.ONE;

            for (int i = 1; ; i++) {
                ifacN = ifacN.multiply(new BigInteger("" + (2 * i - 1)));
                ifacD = ifacD.multiply(new BigInteger("" + (2 * i)));
                xpowi = multiplyRound(xpowi, xhighprSq);
                BigDecimal c = divideRound(multiplyRound(xpowi, ifacN),
                        ifacD.multiply(new BigInteger("" + (2 * i + 1))));
                resul = resul.add(c);
                if (Math.abs(c.doubleValue()) < 0.1 * eps)
                    break;
            }
            MathContext mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return resul.round(mc);
        }
    } /* BigDecimalMath.asin */

    /**
     * The inverse trigonometric cosine.
     *
     * @param x the argument.
     * @return the arccos(x) in radians.
     * @author Richard J. Mathar
     * @since 2009-09-29
     */
    static public BigDecimal acos(final BigDecimal x) {
        /* Essentially forwarded to pi/2 - asin(x)
         */
        final BigDecimal xhighpr = scalePrec(x, 2);
        BigDecimal resul = asin(xhighpr);
        double eps = resul.ulp().doubleValue() / 2.;

        MathContext mc = new MathContext(err2prec(3.14159, eps));
        BigDecimal pihalf = PI.divide(new BigDecimal(2));
        resul = pihalf.subtract(resul);

        /* absolute error in the result is err(x)/sqrt(1-x^2) to lowest order
         */
        final double xDbl = x.doubleValue();
        final double xUlpDbl = x.ulp().doubleValue() / 2.;
        eps = xUlpDbl / 2. / Math.sqrt(1. - Math.pow(xDbl, 2.));

        mc = new MathContext(err2prec(resul.doubleValue(), eps));
        return resul.round(mc);

    } /* BigDecimalMath.acos */

    /**
     * The inverse trigonometric tangent.
     *
     * @param x the argument.
     * @return the principal value of arctan(x) in radians in the range -pi/2 to +pi/2.
     * @author Richard J. Mathar
     * @since 2009-08-03
     */
    static public BigDecimal atan(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            return atan(x.negate()).negate();
        } else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else if (x.doubleValue() > 0.7 && x.doubleValue() < 3.0) {
            /* Abramowitz-Stegun 4.4.34 convergence acceleration
             * 2*arctan(x) = arctan(2x/(1-x^2)) = arctan(y).  x=(sqrt(1+y^2)-1)/y
             * This maps 0<=y<=3 to 0<=x<=0.73 roughly. Temporarily with 2 protectionist digits.
             */
            BigDecimal y = scalePrec(x, 2);
            BigDecimal newx = divideRound(hypot(1, y).subtract(BigDecimal.ONE), y);

            /* intermediate result with too optimistic error estimate*/
            BigDecimal resul = multiplyRound(atan(newx), 2);

            /* absolute error in the result is errx/(1+x^2), where errx = half  of the ulp. */
            double eps = x.ulp().doubleValue() / (2.0 * Math.hypot(1.0, x.doubleValue()));
            MathContext mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return resul.round(mc);
        } else if (x.doubleValue() < 0.71) {
            /* Taylor expansion around x=0; Abramowitz-Stegun 4.4.42 */

            final BigDecimal xhighpr = scalePrec(x, 2);
            final BigDecimal xhighprSq = multiplyRound(xhighpr, xhighpr).negate();

            BigDecimal resul = xhighpr.plus();

            /* signed x^(2i+1) */
            BigDecimal xpowi = xhighpr;

            /* absolute error in the result is errx/(1+x^2), where errx = half  of the ulp.
             */
            double eps = x.ulp().doubleValue() / (2.0 * Math.hypot(1.0, x.doubleValue()));

            for (int i = 1; ; i++) {
                xpowi = multiplyRound(xpowi, xhighprSq);
                BigDecimal c = divideRound(xpowi, 2 * i + 1);

                resul = resul.add(c);
                if (Math.abs(c.doubleValue()) < 0.1 * eps)
                    break;
            }
            MathContext mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return resul.round(mc);
        } else {
            /* Taylor expansion around x=infinity; Abramowitz-Stegun 4.4.42 */

            /* absolute error in the result is errx/(1+x^2), where errx = half  of the ulp.
             */
            double eps = x.ulp().doubleValue() / (2.0 * Math.hypot(1.0, x.doubleValue()));

            /* start with the term pi/2; gather its precision relative to the expected result
             */
            MathContext mc = new MathContext(2 + err2prec(3.1416, eps));
            BigDecimal onepi = PI;
            BigDecimal resul = onepi.divide(new BigDecimal(2));

            final BigDecimal xhighpr = divideRound(-1, scalePrec(x, 2));
            final BigDecimal xhighprSq = multiplyRound(xhighpr, xhighpr).negate();

            /* signed x^(2i+1) */
            BigDecimal xpowi = xhighpr;

            for (int i = 0; ; i++) {
                BigDecimal c = divideRound(xpowi, 2 * i + 1);

                resul = resul.add(c);
                if (Math.abs(c.doubleValue()) < 0.1 * eps)
                    break;
                xpowi = multiplyRound(xpowi, xhighprSq);
            }
            mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return resul.round(mc);
        }
    } /* BigDecimalMath.atan */

    /**
     * The hyperbolic cosine.
     *
     * @param x The argument.
     * @return The cosh(x) = (exp(x)+exp(-x))/2 .
     * @author Richard J. Mathar
     * @since 2009-08-19
     * @since 2015-02-09 corrected result for negative arguments.
     */
    static public BigDecimal cosh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return cosh(x.negate());
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ONE;
        else {
            if (x.doubleValue() > 1.5) {
                /* cosh^2(x) = 1+ sinh^2(x).
                 */
                return hypot(1, sinh(x));
            } else {
                BigDecimal xhighpr = scalePrec(x, 2);
                /* Simple Taylor expansion, sum_{0=1..infinity} x^(2i)/(2i)! */
                BigDecimal resul = BigDecimal.ONE;

                /* x^i */
                BigDecimal xpowi = BigDecimal.ONE;

                /* 2i factorial */
                BigInteger ifac = BigInteger.ONE;

                /* The absolute error in the result is the error in x^2/2 which is x times the error in x.
                 */
                double xUlpDbl = 0.5 * x.ulp().doubleValue() * x.doubleValue();

                /* The error in the result is set by the error in x^2/2 itself, xUlpDbl.
                 * We need at most k terms to push x^(2k)/(2k)! below this value.
                 * x^(2k) < xUlpDbl; (2k)*log(x) < log(xUlpDbl);
                 */
                int k = (int) (Math.log(xUlpDbl) / Math.log(x.doubleValue())) / 2;

                /* The individual terms are all smaller than 1, so an estimate of 1.0 for
                 * the absolute value will give a safe relative error estimate for the indivdual terms
                 */
                MathContext mcTay = new MathContext(err2prec(1., xUlpDbl / k));
                for (int i = 1; ; i++) {
                    /* TBD: at which precision will 2*i-1 or 2*i overflow?
                     */
                    ifac = ifac.multiply(new BigInteger("" + (2 * i - 1)));
                    ifac = ifac.multiply(new BigInteger("" + (2 * i)));
                    xpowi = xpowi.multiply(xhighpr).multiply(xhighpr);
                    BigDecimal corr = xpowi.divide(new BigDecimal(ifac), mcTay);
                    resul = resul.add(corr);
                    if (corr.abs().doubleValue() < 0.5 * xUlpDbl)
                        break;
                }
                /* The error in the result is governed by the error in x itself.
                 */
                MathContext mc = new MathContext(err2prec(resul.doubleValue(), xUlpDbl));
                return resul.round(mc);
            }
        }
    } /* BigDecimalMath.cosh */

    /**
     * The hyperbolic sine.
     *
     * @param x the argument.
     * @return the sinh(x) = (exp(x)-exp(-x))/2 .
     * @author Richard J. Mathar
     * @since 2009-08-19
     */
    static public BigDecimal sinh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return sinh(x.negate()).negate();
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            if (x.doubleValue() > 2.4) {
                /* Move closer to zero with sinh(2x)= 2*sinh(x)*cosh(x).
                 */
                BigDecimal two = new BigDecimal(2);
                BigDecimal xhalf = x.divide(two);
                BigDecimal resul = sinh(xhalf).multiply(cosh(xhalf)).multiply(two);
                /* The error in the result is set by the error in x itself.
                 * The first derivative of sinh(x) is cosh(x), so the absolute error
                 * in the result is cosh(x)*errx, and the relative error is coth(x)*errx = errx/tanh(x)
                 */
                double eps = Math.tanh(x.doubleValue());
                MathContext mc = new MathContext(err2prec(0.5 * x.ulp().doubleValue() / eps));
                return resul.round(mc);
            } else {
                BigDecimal xhighpr = scalePrec(x, 2);
                /* Simple Taylor expansion, sum_{i=0..infinity} x^(2i+1)/(2i+1)! */
                BigDecimal resul = xhighpr;

                /* x^i */
                BigDecimal xpowi = xhighpr;

                /* 2i+1 factorial */
                BigInteger ifac = BigInteger.ONE;

                /* The error in the result is set by the error in x itself.
                 */
                double xUlpDbl = x.ulp().doubleValue();

                /* The error in the result is set by the error in x itself.
                 * We need at most k terms to squeeze x^(2k+1)/(2k+1)! below this value.
                 * x^(2k+1) < x.ulp; (2k+1)*log10(x) < -x.precision; 2k*log10(x)< -x.precision;
                 * 2k*(-log10(x)) > x.precision; 2k*log10(1/x) > x.precision
                 */
                int k = (int) (x.precision() / Math.log10(1.0 / xhighpr.doubleValue())) / 2;
                MathContext mcTay = new MathContext(err2prec(x.doubleValue(), xUlpDbl / k));
                for (int i = 1; ; i++) {
                    /* TBD: at which precision will 2*i or 2*i+1 overflow?
                     */
                    ifac = ifac.multiply(new BigInteger("" + (2 * i)));
                    ifac = ifac.multiply(new BigInteger("" + (2 * i + 1)));
                    xpowi = xpowi.multiply(xhighpr).multiply(xhighpr);
                    BigDecimal corr = xpowi.divide(new BigDecimal(ifac), mcTay);
                    resul = resul.add(corr);
                    if (corr.abs().doubleValue() < 0.5 * xUlpDbl)
                        break;
                }
                /* The error in the result is set by the error in x itself.
                 */
                MathContext mc = new MathContext(x.precision());
                return resul.round(mc);
            }
        }
    } /* BigDecimalMath.sinh */

    /**
     * The hyperbolic tangent.
     *
     * @param x The argument.
     * @return The tanh(x) = sinh(x)/cosh(x).
     * @author Richard J. Mathar
     * @since 2009-08-20
     */
    static public BigDecimal tanh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return tanh(x.negate()).negate();
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            BigDecimal xhighpr = scalePrec(x, 2);

            /* tanh(x) = (1-e^(-2x))/(1+e^(-2x)) .
             */
            BigDecimal exp2x = exp(xhighpr.multiply(new BigDecimal(-2)));

            /* The error in tanh x is err(x)/cosh^2(x).
             */
            double eps = 0.5 * x.ulp().doubleValue() / Math.pow(Math.cosh(x.doubleValue()), 2.0);
            MathContext mc = new MathContext(err2prec(Math.tanh(x.doubleValue()), eps));
            return BigDecimal.ONE.subtract(exp2x).divide(BigDecimal.ONE.add(exp2x), mc);
        }
    } /* BigDecimalMath.tanh */

    /**
     * The inverse hyperbolic sine.
     *
     * @param x The argument.
     * @return The arcsinh(x) .
     * @author Richard J. Mathar
     * @since 2009-08-20
     */
    static public BigDecimal asinh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            BigDecimal xhighpr = scalePrec(x, 2);

            /* arcsinh(x) = log(x+hypot(1,x))
             */
            BigDecimal logx = log(hypot(1, xhighpr).add(xhighpr));

            /* The absolute error in arcsinh x is err(x)/sqrt(1+x^2)
             */
            double xDbl = x.doubleValue();
            double eps = 0.5 * x.ulp().doubleValue() / Math.hypot(1., xDbl);
            MathContext mc = new MathContext(err2prec(logx.doubleValue(), eps));
            return logx.round(mc);
        }
    } /* BigDecimalMath.asinh */

    /**
     * The inverse hyperbolic cosine.
     *
     * @param x The argument.
     * @return The arccosh(x) .
     * @author Richard J. Mathar
     * @since 2009-08-20
     */
    static public BigDecimal acosh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ONE) < 0)
            throw new ArithmeticException("Out of range argument cosh " + x.toString());
        else if (x.compareTo(BigDecimal.ONE) == 0)
            return BigDecimal.ZERO;
        else {
            BigDecimal xhighpr = scalePrec(x, 2);

            /* arccosh(x) = log(x+sqrt(x^2-1))
             */
            BigDecimal logx = log(sqrt(xhighpr.pow(2).subtract(BigDecimal.ONE)).add(xhighpr));

            /* The absolute error in arcsinh x is err(x)/sqrt(x^2-1)
             */
            double xDbl = x.doubleValue();
            double eps = 0.5 * x.ulp().doubleValue() / Math.sqrt(xDbl * xDbl - 1.);
            MathContext mc = new MathContext(err2prec(logx.doubleValue(), eps));
            return logx.round(mc);
        }
    } /* BigDecimalMath.acosh */


    /**
     * Pochhammer's  function.
     *
     * @param x The main argument.
     * @param n The non-negative index.
     * @return (x)_n = x(x+1)(x+2)*...*(x+n-1).
     * @author Richard J. Mathar
     * @since 2009-08-19
     */
    static public BigDecimal pochhammer(final BigDecimal x, final int n) {
        /* reduce to interval near 1.0 with the functional relation, Abramowitz-Stegun 6.1.33
         */
        if (n < 0)
            throw new ProviderException("Not implemented: pochhammer with negative index " + n);
        else if (n == 0)
            return BigDecimal.ONE;
        else {
            /* internally two safety digits
             */
            BigDecimal xhighpr = scalePrec(x, 2);
            BigDecimal resul = xhighpr;

            double xUlpDbl = x.ulp().doubleValue();
            double xDbl = x.doubleValue();
            /* relative error of the result is the sum of the relative errors of the factors
             */
            double eps = 0.5 * xUlpDbl / Math.abs(xDbl);
            for (int i = 1; i < n; i++) {
                eps += 0.5 * xUlpDbl / Math.abs(xDbl + i);
                resul = resul.multiply(xhighpr.add(new BigDecimal(i)));
                final MathContext mcloc = new MathContext(4 + err2prec(eps));
                resul = resul.round(mcloc);
            }
            return resul.round(new MathContext(err2prec(eps)));
        }
    } /* BigDecimalMath.pochhammer */

    /**
     * Reduce value to the interval [0,2*Pi].
     *
     * @param x the original value
     * @return the value modulo 2*pi in the interval from 0 to 2*pi.
     * @author Richard J. Mathar
     * @since 2009-06-01
     */
    static public BigDecimal mod2pi(BigDecimal x) {
        /* write x= 2*pi*k+r with the precision in r defined by the precision of x and not
         * compromised by the precision of 2*pi, so the ulp of 2*pi*k should match the ulp of x.
         * First get a guess of k to figure out how many digits of 2*pi are needed.
         */
        int k = (int) (0.5 * x.doubleValue() / Math.PI);

        /* want to have err(2*pi*k)< err(x)=0.5*x.ulp, so err(pi) = err(x)/(4k) with two safety digits
         */
        double err2pi;
        if (k != 0)
            err2pi = 0.25 * Math.abs(x.ulp().doubleValue() / k);
        else
            err2pi = 0.5 * Math.abs(x.ulp().doubleValue());
        MathContext mc = new MathContext(2 + err2prec(6.283, err2pi));
        BigDecimal twopi = PI.multiply(new BigDecimal(2));

        /* Delegate the actual operation to the BigDecimal class, which may return
         * a negative value of x was negative .
         */
        BigDecimal res = x.remainder(twopi);
        if (res.compareTo(BigDecimal.ZERO) < 0)
            res = res.add(twopi);

        /* The actual precision is set by the input value, its absolute value of x.ulp()/2.
         */
        mc = new MathContext(err2prec(res.doubleValue(), x.ulp().doubleValue() / 2.));
        return res.round(mc);
    } /* mod2pi */

    /**
     * Reduce value to the interval [-Pi/2,Pi/2].
     *
     * @param x The original value
     * @return The value modulo pi, shifted to the interval from -Pi/2 to Pi/2.
     * @author Richard J. Mathar
     * @since 2009-07-31
     */
    static public BigDecimal modpi(BigDecimal x) {
        /* write x= pi*k+r with the precision in r defined by the precision of x and not
         * compromised by the precision of pi, so the ulp of pi*k should match the ulp of x.
         * First get a guess of k to figure out how many digits of pi are needed.
         */
        int k = (int) (x.doubleValue() / Math.PI);

        /* want to have err(pi*k)< err(x)=x.ulp/2, so err(pi) = err(x)/(2k) with two safety digits
         */
        double errpi;
        if (k != 0)
            errpi = 0.5 * Math.abs(x.ulp().doubleValue() / k);
        else
            errpi = 0.5 * Math.abs(x.ulp().doubleValue());
        MathContext mc = new MathContext(2 + err2prec(3.1416, errpi));
        BigDecimal onepi = PI;
        BigDecimal pihalf = onepi.divide(new BigDecimal(2));

        /* Delegate the actual operation to the BigDecimal class, which may return
         * a negative value of x was negative .
         */
        BigDecimal res = x.remainder(onepi);
        if (res.compareTo(pihalf) > 0)
            res = res.subtract(onepi);
        else if (res.compareTo(pihalf.negate()) < 0)
            res = res.add(onepi);

        /* The actual precision is set by the input value, its absolute value of x.ulp()/2.
         */
        mc = new MathContext(err2prec(res.doubleValue(), x.ulp().doubleValue() / 2.));
        return res.round(mc);
    } /* modpi */


    /**
     * trigonometric cot.
     *
     * @param x The argument.
     * @return cot(x) = 1/tan(x).
     * @author Richard J. Mathar
     */
    static public double cot(final double x) {
        return 1. / Math.tan(x);
    }


    /**
     * Add a BigDecimal and a BigInteger.
     *
     * @param x The left summand
     * @param y The right summand
     * @return The sum x+y.
     * @author Richard J. Mathar
     * @since 2012-03-02
     */
    static public BigDecimal add(final BigDecimal x, final BigInteger y) {
        return x.add(new BigDecimal(y));
    } /* add */


    /**
     * Add and round according to the larger of the two ulp's.
     *
     * @param x The left summand
     * @param y The right summand
     * @return The sum x+y.
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal addRound(final BigDecimal x, final BigDecimal y) {
        BigDecimal resul = x.add(y);
        /* The estimation of the absolute error in the result is |err(y)|+|err(x)|
         */
        double errR = Math.abs(y.ulp().doubleValue() / 2.) + Math.abs(x.ulp().doubleValue() / 2.);
        MathContext mc = new MathContext(err2prec(resul.doubleValue(), errR));
        return resul.round(mc);
    } /* addRound */


    /**
     * Subtract and round according to the larger of the two ulp's.
     *
     * @param x The left term.
     * @param y The right term.
     * @return The difference x-y.
     * @since 2009-07-30
     */
    static public BigDecimal subtractRound(final BigDecimal x, final BigDecimal y) {
        BigDecimal resul = x.subtract(y);
        /* The estimation of the absolute error in the result is |err(y)|+|err(x)|
         */
        double errR = Math.abs(y.ulp().doubleValue() / 2.) + Math.abs(x.ulp().doubleValue() / 2.);
        MathContext mc = new MathContext(err2prec(resul.doubleValue(), errR));
        return resul.round(mc);
    } /* subtractRound */


    /**
     * Multiply and round.
     *
     * @param x The left factor.
     * @param y The right factor.
     * @return The product x*y.
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal multiplyRound(final BigDecimal x, final BigDecimal y) {
        BigDecimal resul = x.multiply(y);
        /* The estimation of the relative error in the result is the sum of the relative
         * errors |err(y)/y|+|err(x)/x|
         */
        MathContext mc = new MathContext(Math.min(x.precision(), y.precision()));
        return resul.round(mc);
    } /* multiplyRound */


    /**
     * Multiply and round.
     *
     * @param x The left factor.
     * @param n The right factor.
     * @return The product x*n.
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal multiplyRound(final BigDecimal x, final int n) {
        BigDecimal resul = x.multiply(new BigDecimal(n));
        /* The estimation of the absolute error in the result is |n*err(x)|
         */
        MathContext mc = new MathContext(n != 0 ? x.precision() : 0);
        return resul.round(mc);
    }

    /**
     * Multiply and round.
     *
     * @param x The left factor.
     * @param n The right factor.
     * @return the product x*n
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal multiplyRound(final BigDecimal x, final BigInteger n) {
        BigDecimal resul = x.multiply(new BigDecimal(n));
        /* The estimation of the absolute error in the result is |n*err(x)|
         */
        MathContext mc = new MathContext(n.compareTo(BigInteger.ZERO) != 0 ? x.precision() : 0);
        return resul.round(mc);
    }

    /**
     * Divide and round.
     *
     * @param x The numerator
     * @param y The denominator
     * @return the divided x/y
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal divideRound(final BigDecimal x, final BigDecimal y) {
        /* The estimation of the relative error in the result is |err(y)/y|+|err(x)/x|
         */
        MathContext mc = new MathContext(Math.min(x.precision(), y.precision()));
        BigDecimal resul = x.divide(y, mc);
        /* If x and y are precise integer values that may have common factors,
         * the method above will truncate trailing zeros, which may result in
         * a smaller apparent accuracy than starte... add missing trailing zeros now.
         */
        return scalePrec(resul, mc);
    }

    /**
     * Divide and round.
     *
     * @param x The numerator
     * @param n The denominator
     * @return the divided x/n
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal divideRound(final BigDecimal x, final int n) {
        /* The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return x.divide(new BigDecimal(n), mc);
    }

    /**
     * Divide and round.
     *
     * @param x The numerator
     * @param n The denominator
     * @return the divided x/n
     * @author Richard J. Mathar
     * @since 2009-07-30
     */
    static public BigDecimal divideRound(final BigDecimal x, final BigInteger n) {
        /* The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return x.divide(new BigDecimal(n), mc);
    } /* divideRound */

    /**
     * Divide and round.
     *
     * @param n The numerator
     * @param x The denominator
     * @return the divided n/x
     * @author Richard J. Mathar
     * @since 2009-08-05
     */
    static public BigDecimal divideRound(final BigInteger n, final BigDecimal x) {
        /* The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return new BigDecimal(n).divide(x, mc);
    } /* divideRound */


    /**
     * Divide and round.
     *
     * @param n The numerator.
     * @param x The denominator.
     * @return the divided n/x.
     * @author Richard J. Mathar
     * @since 2009-08-05
     */
    static public BigDecimal divideRound(final int n, final BigDecimal x) {
        /* The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return new BigDecimal(n).divide(x, mc);
    }

    /**
     * Append decimal zeros to the value. This returns a value which appears to have
     * a higher precision than the input.
     *
     * @param x The input value
     * @param d The (positive) value of zeros to be added as least significant digits.
     * @return The same value as the input but with increased (pseudo) precision.
     * @author Richard J. Mathar
     */
    static public BigDecimal scalePrec(final BigDecimal x, int d) {
        return x.setScale(d + x.scale());
    }

    /**
     * Boost the precision by appending decimal zeros to the value. This returns a value which appears to have
     * a higher precision than the input.
     *
     * @param x  The input value
     * @param mc The requirement on the minimum precision on return.
     * @return The same value as the input but with increased (pseudo) precision.
     * @author Richard J. Mathar
     */
    static public BigDecimal scalePrec(final BigDecimal x, final MathContext mc) {
        final int diffPr = mc.getPrecision() - x.precision();
        if (diffPr > 0)
            return scalePrec(x, diffPr);
        else
            return x;
    } /* BigDecimalMath.scalePrec */

    /**
     * Convert an absolute error to a precision.
     *
     * @param x    The value of the variable
     * @param xerr The absolute error in the variable
     * @return The number of valid digits in x.
     * The value is rounded down, and on the pessimistic side for that reason.
     * @author Richard J. Mathar
     * @since 2009-06-25
     */
    static public int err2prec(BigDecimal x, BigDecimal xerr) {
        return err2prec(xerr.divide(x, MathContext.DECIMAL64).doubleValue());
    }

    /**
     * Convert an absolute error to a precision.
     *
     * @param x    The value of the variable
     *             The value returned depends only on the absolute value, not on the sign.
     * @param xerr The absolute error in the variable
     *             The value returned depends only on the absolute value, not on the sign.
     * @return The number of valid digits in x.
     * Derived from the representation x+- xerr, as if the error was represented
     * in a "half width" (half of the error bar) form.
     * The value is rounded down, and on the pessimistic side for that reason.
     * @author Richard J. Mathar
     * @since 2009-05-30
     */
    static public int err2prec(double x, double xerr) {
        /* Example: an error of xerr=+-0.5 at x=100 represents 100+-0.5 with
         * a precision = 3 (digits).
         */
        return 1 + (int) (Math.log10(Math.abs(0.5 * x / xerr)));
    }

    /**
     * Convert a relative error to a precision.
     *
     * @param xerr The relative error in the variable.
     *             The value returned depends only on the absolute value, not on the sign.
     * @return The number of valid digits in x.
     * The value is rounded down, and on the pessimistic side for that reason.
     * @author Richard J. Mathar
     * @since 2009-08-05
     */
    static public int err2prec(double xerr) {
        /* Example: an error of xerr=+-0.5 a precision of 1 (digit), an error of
         * +-0.05 a precision of 2 (digits)
         */
        return 1 + (int) (Math.log10(Math.abs(0.5 / xerr)));
    }

    /**
     * Convert a precision (relative error) to an absolute error.
     * The is the inverse functionality of err2prec().
     *
     * @param x    The value of the variable
     *             The value returned depends only on the absolute value, not on the sign.
     * @param prec The number of valid digits of the variable.
     * @return the absolute error in x.
     * Derived from the an accuracy of one half of the ulp.
     * @author Richard J. Mathar
     * @since 2009-08-09
     */
    static public double prec2err(final double x, final int prec) {
        return 5. * Math.abs(x) * Math.pow(10., -prec);
    }

} /* BigDecimalMath */
