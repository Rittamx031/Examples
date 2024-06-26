package ecommecre.model.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSignUpRequest {

  @NotBlank(message = "Vui lòng nhập trường này!")
  @NotNull(message = "Vui lòng nhập trường này!")
  String email;
  @NotBlank(message = "Vui lòng nhập trường này!")
  @NotNull(message = "Vui lòng nhập trường này!")
  String name;
  @NotBlank(message = "Vui lòng nhập trường này!")
  @NotNull(message = "Vui lòng nhập trường này!")
  String phone;
  @NotBlank(message = "Vui lòng nhập trường này!")
  @NotNull(message = "Vui lòng nhập trường này!")
  @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,8}$", message = "Mật khẩu từ 6 - 8 kí tự và phải chứa 1 kí tự số và 1 ký tự viết hoa")
  String password;
  @NotBlank(message = "Vui lòng nhập trường này!")
  @NotNull(message = "Vui lòng nhập trường này!")
  @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,8}$", message = "Mật khẩu từ 6 - 8 kí tự và phải chứa 1 kí tự số và 1 ký tự viết hoa")
  String passwordRepeat;
  @NotNull(message = "Vui lòng nhập trường này!")
  boolean sex;
  @NotNull(message = "trường này không thể để trống")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @PastOrPresent(message = "Ngày sinh không hợp lệ")
  LocalDate birthDay;

  public String ValidateError() {
    String errors = "";
    if (!password.equals(passwordRepeat)) {
      errors += "Password not Match";
    }
    return errors;
  }

  public boolean validateHasError() {
    if (!password.equals(passwordRepeat)) {
      return true;
    }
    return false;
  }
}
