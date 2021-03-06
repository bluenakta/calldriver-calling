
package calldriver.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="pay", url="http://admin13-payment:8080")
public interface PayService {

    @RequestMapping(method= RequestMethod.POST, path="/pays")
    public void payRequest(@RequestBody Pay pay);

}