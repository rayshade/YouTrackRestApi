package youtrack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by egor.malyshev on 01.04.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "comments")
public class CommentList {
	@XmlElement(name = "comment")
	private List<IssueComment> comments;

	CommentList() {
	}

	@Override
	public String toString() {
		return "CommentList{" +
				"comments=" + comments +
				'}';
	}

	public List<IssueComment> getComments() {

		return comments;
	}

}