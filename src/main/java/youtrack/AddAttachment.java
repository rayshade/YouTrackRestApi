package youtrack;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 * Created by egor.malyshev on 03.04.2014.
 */
final class AddAttachment extends AddCommand<Issue, IssueAttachment> {
    AddAttachment(Issue owner) {
        super(owner);
    }

    @Override
    HttpRequestBase createMethod() {
        final HttpPost postMethod = new HttpPost(owner.getYouTrack().getHostAddress() +
                "issue/" + getOwner().getId() + "/attachment?&name=" + getItem().getName() +
                "&files=" + getItem().getUrl());

        assert (getItem().getDataStream()) != null;
        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addTextBody("name", getItem().getName())
                .addBinaryBody("file", getItem().getDataStream(),
                        getItem().getContentType(), FilenameUtils.getName(getItem().getUrl()))
                .build();

        postMethod.setEntity(entity);
        return postMethod;
    }
}