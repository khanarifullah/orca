package com.netflix.spinnaker.orca.sql.pipeline.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.spinnaker.config.CompressionType
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.sql.ResultSet
import java.util.zip.DeflaterOutputStream
import org.assertj.core.api.Assertions.assertThat

class ExecutionMapperTest : JUnit5Minutests {

  fun tests() = rootContext<Unit> {

    context("handle body decompression") {
      val mapper = ExecutionMapper(mapper = ObjectMapper(), stageBatchSize = 200)

      val mockedResultSet = mock<ResultSet>()

      test("decompression ignored when compressed body is null") {
        doReturn("12345").`when`(mockedResultSet).getString("body")
        doReturn(null).`when`(mockedResultSet).getBytes("compressedBody")
        doReturn(CompressionType.ZLIB.toString())
          .`when`(mockedResultSet).getString("compression_type")
        assertThat(mapper.getDecompressedBody(mockedResultSet)).isEqualTo("12345")
      }

      test("decompression performed when compressed body is not null") {
        val compressedBodyByteStream = ByteArrayOutputStream()
        DeflaterOutputStream(compressedBodyByteStream).bufferedWriter(StandardCharsets.UTF_8).use {
          it.write("12345", 0, 5)
        }
        doReturn("").`when`(mockedResultSet).getString("body")
        doReturn(compressedBodyByteStream.toByteArray())
          .`when`(mockedResultSet).getBytes("compressed_body")
        doReturn(CompressionType.ZLIB.toString())
          .`when`(mockedResultSet).getString("compression_type")
        assertThat(mapper.getDecompressedBody(mockedResultSet)).isEqualTo("12345")
      }
    }
  }
}
