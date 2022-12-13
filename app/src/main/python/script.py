import grequests
from bs4 import BeautifulSoup
import warnings
warnings.filterwarnings(action='ignore', category=Warning)


def main(zodiac_sign: int, day: str, fetch_timeout=None, is_json=True, abort_on_error=False):
    url_2 = (
        "https://www.horoscope.com/us/horoscopes/general/"
        f"horoscope-general-daily-{day}.aspx?sign={zodiac_sign}"
    )
    try:
        r = grequests.map((grequests.AsyncRequest(url=url_2, method="Get", timeout=fetch_timeout, verify=False),))[0]
        if r is None:
            raise Exception("result is None")
    except Exception as e:
        raise Exception("Got get_url request error: %s" % e)
    else:
        if r.status_code != 200 and abort_on_error:
            raise Exception("Bad status code returned: '%s'. result body: '%s'." % (r.status_code, r.text))
    try:
        if r.text and is_json:
            return r.json()
    except Exception as e:
        print(e)
        pass
    else:
        # print(r.text)
        return r.text

    soup = BeautifulSoup(r.text, "html.parser")
    # print(soup.find("div", class_="main-horoscope").p.text)
    return soup.find("div", class_="main-horoscope").p.text
