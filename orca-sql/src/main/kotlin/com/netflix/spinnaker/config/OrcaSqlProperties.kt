/*
 * Copyright 2018 Netflix, Inc.	 * Copyright 2018 Netflix, Inc.
 *	 *
 * Licensed under the Apache License, Version 2.0 (the "License");	 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.	 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at	 * You may obtain a copy of the License at
 *	 *
 *    http://www.apache.org/licenses/LICENSE-2.0	 *    http://www.apache.org/licenses/LICENSE-2.0
 *	 *
 * Unless required by applicable law or agreed to in writing, software	 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,	 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and	 * See the License for the specific language governing permissions and
 * limitations under the License.	 * limitations under the License.
 */	 */
package com.netflix.spinnaker.config	package com.netflix.spinnaker.config
import org.springframework.boot.context.properties.ConfigurationProperties	import org.springframework.boot.context.properties.ConfigurationProperties
/**	/**
 * @param partitionName Multi-region partitioning; unused presently	 * @param partitionName Multi-region partitioning; unused presently
 * @param batchReadSize Defines the internal page size for large select scans	 * @param batchReadSize Defines the internal page size for large select scans
 * @param enableExecutionBodyCompression Enables execution body compression
 *                                       for bodies larger than maxExecutionBodySize
 * @param maxExecutionBodySize Defines the maximum size of the execution body, in bytes,
 *                             beyond which the body will be compressed
 */	 */
@ConfigurationProperties("sql")	@ConfigurationProperties("sql")
class OrcaSqlProperties {	class OrcaSqlProperties {
  var partitionName: String? = null	  var partitionName: String? = null
  var batchReadSize: Int = 10	  var batchReadSize: Int = 10
  var stageReadSize: Int = 200	  var stageReadSize: Int = 200

  // Pipeline/Stage execution body size configs
  var enableBodyCompression: Boolean = false
  var bodyCompressionThreshold: Int = 1024
}	}