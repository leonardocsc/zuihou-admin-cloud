package com.github.zuihou;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.github.zuihou.auth.utils.JwtHelper;
import com.github.zuihou.auth.utils.JwtUserInfo;
import com.github.zuihou.auth.utils.Token;
import com.github.zuihou.authority.controller.test.HiberDTO;

import org.hibernate.validator.HibernateValidator;
import org.junit.Test;

/**
 * This is a Description
 *
 * @author zuihou
 * @date 2019/07/05
 */
public class NoBootTest {

    public Validator getValidator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //快速失败返回模式
                .addProperty("hibernate.validator.fail_fast", "false")
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Test
    public void test() {

        HiberDTO dto = new HiberDTO();
        dto.setAge(1L).setCode("1").setNotnull(1L).setEmail("q@q").setUrl("http:");
        Set<ConstraintViolation<HiberDTO>> validate = getValidator().validate(dto);
        for (ConstraintViolation<HiberDTO> c : validate) {
            System.out.println(c.getMessage());
        }
    }

    @Test
    public void test2() {
        JwtUserInfo jwtInfo = new JwtUserInfo();
        jwtInfo.setAccount("111");
        jwtInfo.setName("123");
        jwtInfo.setOrgId(1L);
        jwtInfo.setStationId(1L);
        jwtInfo.setUserId(1L);
        Token token = JwtHelper.generateUserToken(jwtInfo, "client/pub.key", 2700);
        System.out.println(token);
    }
}
