/*
 * Copyright (c) 2013 Cosmin Stejerean, Karl Heinz Marbaise, and contributors.
 *
 * Distributed under the MIT license: http://opensource.org/licenses/MIT
 */

package com.offbytwo.jenkins.model;

import java.util.List;

/**
 * This class is only needed to get all builds in
 * {@link MavenJobWithDetails#getAllBuilds()}.
 * 
 * @author Karl Heinz Marbaise
 *
 *         NOTE: This class is not part of any public API
 */
class AllPipelineBuilds extends BaseModel {
    private List<PipelineBuild> allBuilds;

    public AllPipelineBuilds() {
    }

    public List<PipelineBuild> getAllBuilds() {
        return this.allBuilds;
    }
}