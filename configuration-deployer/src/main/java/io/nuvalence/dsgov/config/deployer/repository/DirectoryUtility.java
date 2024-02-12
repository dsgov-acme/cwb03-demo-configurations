package io.nuvalence.dsgov.config.deployer.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DirectoryUtility {

    static List<File> getMatchingFiles(final File dir, final String pattern) {
        final File[] results = dir.listFiles((FileFilter) new WildcardFileFilter(pattern));
        return results == null ? Collections.emptyList() : Arrays.asList(results);
    }
}
