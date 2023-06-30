package ar.edu.unq.desapp.groupb.cryptop2p

import org.apache.commons.lang3.time.StopWatch
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.time.LocalDate

@Aspect
@Component
class LoggingAspect {
    private val logger: Logger = LogManager.getLogger(LoggingAspect::class.java)

    //AOP expression for which methods shall be intercepted
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    @Throws(Throwable::class)
    fun profileAllMethods(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val stopWatch = StopWatch()
        stopWatch.start()
        val methodSignature: MethodSignature = proceedingJoinPoint.signature as MethodSignature
        //TODO: Funcion de busqueda email val user = getEmail(proceedingJoinPoint)
        //Get intercepted method details
        val className: String = methodSignature.declaringType.simpleName
        val methodName: String = methodSignature.name
        //Measure method execution time
        val result = proceedingJoinPoint.proceed()
        val params = (proceedingJoinPoint.signature as MethodSignature).parameterNames.toList()
        stopWatch.stop()
        //Log method execution time
        logger.info("Request made: " + LocalDate.now() + " - $className" + "." + methodName + " - params: $params " + " :: " + stopWatch.time + " ms")
        return result
    }
}