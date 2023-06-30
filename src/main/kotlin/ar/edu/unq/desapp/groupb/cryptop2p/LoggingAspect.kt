package ar.edu.unq.desapp.groupb.cryptop2p

import org.apache.commons.lang3.time.StopWatch
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component


@Aspect
@Component
class LoggingAspect {
    private val logger: Logger = LogManager.getLogger(LoggingAspect::class.java)

    // AOP expression for which methods shall be intercepted
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    @Throws(Throwable::class)
    fun profileAllMethods(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val stopWatch = StopWatch()
        stopWatch.start()

        // Get authentication from security context
        val authentication = SecurityContextHolder.getContext().authentication
        val userName = authentication.name

        // Get intercepted method details
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        val className = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name
        val params = methodSignature.parameterNames.toList()
        val args = proceedingJoinPoint.args

        // Map param names with args values
        assert(args.size == params.size)
        val paramArgs = mutableListOf<String>()
        args.indices.forEach { argIndex ->
            paramArgs.add("${params[argIndex]} = ${args[argIndex]}")
        }

        // Get intercepted method result
        val result = proceedingJoinPoint.proceed()

        // Measure intercepted method execution time
        stopWatch.stop()

        // Log intercepted method execution time
        logger.info("User: $userName :: $className.$methodName(${paramArgs.joinToString(", ")}) :: Execution time: ${stopWatch.time} ms")

        return result
    }
}
