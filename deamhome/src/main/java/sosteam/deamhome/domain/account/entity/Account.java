package sosteam.deamhome.domain.account.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sosteam.deamhome.domain.faq.entity.Faq;
import sosteam.deamhome.domain.item.entity.Wishlist;
import sosteam.deamhome.domain.review.entity.Review;
import sosteam.deamhome.global.attribute.Role;
import sosteam.deamhome.global.attribute.SNS;
import sosteam.deamhome.global.entity.LogEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends LogEntity implements UserDetails {

    @NotNull
    @Indexed(unique = true)
    private String userId;

    @NotNull
    private LocalDateTime birthday;

    @NotNull
    private boolean sex;

    @NotNull
    private String zipcode;

    @NotNull
    private String address1;

    @NotNull
    private String address2;

    @NotNull
    private String address3;

    @NotNull
    private String address4;

    @NotNull
    private String pwd;

    @NotNull
    @Indexed(unique = true)
    private String email;

    @NotNull
    private boolean receiveEmail;

    @NotNull
    private String createdIP;

    @NotNull
    private String adminTxt;

    @NotNull
    private String snsId;

    @NotNull
    private SNS sns = SNS.NORMAL;

    @NotNull
    @Indexed(unique = true)
    private String phone;

    @NotNull
    @Indexed(unique = true)
    private String userName;

    private int point = 0;

    @NotNull
    private Role role = Role.ROLE_GUEST;

    @DBRef(lazy = true)
    @Setter
    private AccountStatus status;

    @DBRef(lazy = true)
    private List<Faq> faq;

    @DBRef(lazy = true)
    @Setter
    private Wishlist wishlist;

    @DBRef(lazy = true)
    private List<Review> reviews;

    public List<Faq> addFaq(Faq faq) {
        this.faq.add(faq);

        return this.faq;
    }

    public List<Review> addReview(Review review) {
        reviews.add(review);

        return this.reviews;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
