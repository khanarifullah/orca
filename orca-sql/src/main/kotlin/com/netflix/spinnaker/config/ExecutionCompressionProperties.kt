package com.netflix.spinnaker.config

import java.io.InputStream
import java.io.OutputStream
import java.util.zip.DeflaterOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.InflaterInputStream
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Defines properties to be used when compressing large execution bodies
 * These properties apply when execution bodies are being upserted for
 * 1. Orchestrations
 * 2. Orchestration stages
 * 3. Pipelines
 * 4. Pipeline Stages
 */
@ConfigurationProperties("execution-repository.sql.compression")
class ExecutionCompressionProperties {

  /**
   * Enables execution body compression for large stage and pipeline execution bodies
   */
  var enabled: Boolean = false

  /**
   * Defines the body size threshold, in bytes, above which the body will be compressed before
   * upsertion
   * Defaults to 1 MB
   */
  var bodyCompressionThreshold: Long = 1048576

  /**
   * Controls the library to be used when compressing bodies
   * Defaults to ZLIB https://docs.oracle.com/javase/8/docs/api/java/util/zip/Deflater.html
   */
  var compressionType: CompressionType = CompressionType.ZLIB
}

/**
 * Enum defining the support compression types
 */
enum class CompressionType(val type: String) {
  GZIP("GZIP"),
  ZLIB("ZLIB");

  fun getDeflator(outStream: OutputStream) =
    when (this) {
      GZIP -> GZIPOutputStream(outStream)
      ZLIB -> DeflaterOutputStream(outStream)
    }

  fun getInflator(inStream: InputStream) =
    when (this) {
      GZIP -> GZIPInputStream(inStream)
      ZLIB -> InflaterInputStream(inStream)
    }
}
