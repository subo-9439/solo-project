package api.vi;


import api.vi.user.UserController;
import api.vi.user.UserMapper;
import api.vi.user.dto.RequestDto;
import api.vi.user.dto.UserResponseDto;
import api.vi.user.entity.Company;
import api.vi.user.entity.Sex;
import api.vi.user.entity.User;
import api.vi.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static api.vi.util.ApiDocumentUtils.getRequestPreProcessor;
import static api.vi.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.apache.coyote.http11.Constants.a;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)//@SpringbootTest ?????? @WebMvcTest ?????????????????? Controller??? ????????? ?????? ?????? ?????? ????????????????????????.(????????? bean???)
@MockBean(JpaMetamodelMappingContext.class) //JPA?????? ???????????? Bean ?????? Mock ????????? ??????????????? ???????????????.
@AutoConfigureRestDocs
public class UserControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @Autowired
    private Gson gson;

    @Test
    public void getUsersTest() throws Exception{
        //given
        User user1 = User.builder()
                .name("?????????")
                .password("1234")
                .sex(Sex.MAN)
                .company(Company.builder().companyName("??????????????????").companyLocation("005").companyType("001").build())
                .build();
        User user2 = User.builder()
                .name("?????????")
                .password("11111")
                .sex(Sex.WOMAN)
                .company(Company.builder().companyName("ABC??????").companyLocation("005").companyType("002").build())
                .build();

 ;
        String page = "1";
        String size = "10";
        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);
        Company company = new Company("??????????????????","005","001");
        Page<User> userPage = new PageImpl<>(List.of(user1,user2), PageRequest.of(0,10),1);
        List<UserResponseDto> responseDtos = List.of(
                new UserResponseDto(
                        "?????????",
                        "1234",
                        Sex.MAN,
                        company),
                new UserResponseDto(
                        "?????????",
                        "11111",
                        Sex.WOMAN,
                        company)
        );
        given(userService.getUserPage(Mockito.anyInt(),Mockito.anyInt())).willReturn(userPage);
        given(userMapper.userToUserResponseDtoByList(Mockito.anyList())).willReturn(responseDtos);
        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users").params(queryParams));

        //then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-users",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestParameters(
                                        List.of(
                                        parameterWithName("page").description("Page ??????"),
                                        parameterWithName("size").description("Page size")
                                        )
                                ),
                                responseFields(
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].password").type(JsonFieldType.STRING).description("?????? ????????????"),
                                        fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("?????? ?????? : M(??????) / W(??????) / T(??? 3??????)"),
                                        fieldWithPath("data[].company").type(JsonFieldType.OBJECT).description("????????? ????????? ????????????"),
                                        fieldWithPath("data[].company.companyName").type(JsonFieldType.STRING).description("????????? ????????? ????????????"),
                                        fieldWithPath("data[].company.companyType").type(JsonFieldType.STRING).description("????????? ????????? ????????????"),
                                        fieldWithPath("data[].company.companyLocation").type(JsonFieldType.STRING).description("????????? ????????? ????????????"),

                                        fieldWithPath("pageInfo.page").description("???????????????"),
                                        fieldWithPath("pageInfo.size").description("??????????????????"),
                                        fieldWithPath("pageInfo.totalElements").description("??? ?????????"),
                                        fieldWithPath("pageInfo.totalPages").description("??? ????????? ???")

                                )
                        )
                )
                .andReturn();
    }

    @Test
    public void getUserTest() throws Exception{

        //given
        User user1 = User.builder()
                .name("?????????")
                .password("1234")
                .sex(Sex.MAN)
                .company(Company.builder().companyName("??????????????????").companyLocation("005").companyType("001").build())
                .build();
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name("?????????")
                .password("1234")
                .sex(Sex.MAN)
                .company(Company.builder().companyName("??????????????????").companyLocation("005").companyType("001").build())
                .build();
        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("location","005");
        queryParams.add("business","001");
        RequestDto requestDto = new RequestDto("005","001");
        given(userService.getUser(Mockito.any(RequestDto.class))).willReturn(user1);
        given(userMapper.userToUserResponseDto(Mockito.any(User.class))).willReturn(userResponseDto);
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/user").params(queryParams));
        System.out.println(actions.andReturn().toString());
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(userResponseDto.getName()))
                .andExpect(jsonPath("$.data.password").value(userResponseDto.getPassword()))
                .andExpect(jsonPath("$.data.sex").value(userResponseDto.getSex().toString()))
                .andExpect(jsonPath("$.data.company.companyName").value(userResponseDto.getCompany().getCompanyName()))
                .andDo(document(
                        "get-user",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("location").description("????????? ????????? ??????"),
                                parameterWithName("business").description("????????? ????????? ??????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("?????? ????????????"),
                                        fieldWithPath("data.sex").type(JsonFieldType.STRING).description("?????? ?????? : M(??????) / W(??????) / T(??? 3??????)"),
                                        fieldWithPath("data.company").type(JsonFieldType.OBJECT).description("????????? ????????? ????????????"),
                                        fieldWithPath("data.company.companyName").type(JsonFieldType.STRING).description("????????? ????????? ????????????"),
                                        fieldWithPath("data.company.companyType").type(JsonFieldType.STRING).description("????????? ????????? ????????????"),
                                        fieldWithPath("data.company.companyLocation").type(JsonFieldType.STRING).description("????????? ????????? ????????????")
                                )
                        )
                ));

    }
}
