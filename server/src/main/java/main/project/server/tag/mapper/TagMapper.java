package main.project.server.tag.mapper;

import org.mapstruct.Mapper;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public interface TagMapper {

    /** 배열로 넘어온 태그를 정렬하여 DB에 저장되는 포맷의 형태로 변환시켜 주는 메소드 **/
    default String createSortedTagString(String[] tags) {

        if(tags == null)
            return null;

        Arrays.sort(tags);

        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tags) {

            stringBuilder.append("|" + tag + "|");
        }
        return stringBuilder.toString();
    }

    /** DB에 저장되어 있는 ||포맷형태의 문자열 형태의 태그를 문자열배열로 변환하는 메소드 **/
    default String[] createSortedTagArray(String tags) {

        String[] splStr;

        if(tags == null) //태그가 없는 경우
            return new String[0];

        if (tags.contains("||")) { //2개 이상 경우

            splStr = tags.split("\\|\\|");
            // |dd, ddd|, ...
        }
        else { //1개 경우

            splStr = new String[]{tags};
            // |dd|
        }

        String[] tagArray = Arrays.stream(splStr).map(str ->
                new String(str.replace("|", ""))).toArray(str -> new String[str]);

        return tagArray;
    }
}
