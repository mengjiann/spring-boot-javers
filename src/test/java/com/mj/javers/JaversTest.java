package com.mj.javers;

import com.google.common.collect.ImmutableMap;
import com.mj.javers.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(JUnit4.class)
public class JaversTest {

    private Javers javers;

    @Before
    public void setUp() throws Exception {

        this.javers = JaversBuilder.javers().build();

        ImmutableMap<String, String> sampleMap1 = ImmutableMap.of("sample-key-1","sample-value-1");
        ImmutableMap<String, String> sampleMap2 = ImmutableMap.of("sample-key-2","sample-value-2");

        javers.commit("user-1", new Book(1, "Forest","Mark","Times"),sampleMap1);
        javers.commit("user-2", new Book(1, "Forest","Mark and Twain","News"),sampleMap1);
        javers.commit("user-2", new Book(1, "Forest","Mark and John","Times"),sampleMap2);

        javers.commit("user-1", new Book(2, "Ocean","Mark","Times"),sampleMap1);
        javers.commit("user-2", new Book(2, "Ocean","Mark and Twain","Times"),sampleMap1);

    }

    @Test
    public void getChangeForOneProperty() {

        JqlQuery jqlQuery = QueryBuilder.byInstanceId(1, Book.class)
                .withChangedProperty("publisher") // only interested in approved
                .build();

        Changes changes = javers.findChanges(jqlQuery);

        assertThat(changes.size()).isEqualTo(2);

        log.info(changes.prettyPrint());
    }

    @Test
    public void verifyUnchangeProperty() {

        JqlQuery jqlQuery = QueryBuilder.byInstanceId(2, Book.class)
                .withChangedProperty("publisher") // only interested in approved
                .build();

        Changes changes = javers.findChanges(jqlQuery);

        assertThat(changes.size()).isEqualTo(0);

        log.info(changes.prettyPrint());
    }

    @Test
    public void getMostRecentSnapshot() {

        JqlQuery jqlQuery = QueryBuilder.byInstanceId(1, Book.class)
                .withCommitProperty("sample-key-1","sample-value-1") // filter based on commit property
                .limit(1) // limit to most recent one
                .build();

        List<CdoSnapshot> cdoSnapshotList = javers.findSnapshots(jqlQuery);

        assertThat(cdoSnapshotList.size()).isEqualTo(1);
        assertThat(cdoSnapshotList.get(0).getCommitMetadata().getAuthor()).isEqualTo("user-2");
        assertThat(cdoSnapshotList.get(0).getChanged()).isEqualTo(Arrays.asList(new String[]{"author","publisher"}));

        log.info(cdoSnapshotList.toString());


    }

}
