import asyncio
import logging
import traceback
from typing import Callable, Any, List
from crons.data_collection_and_notification import fetch_data_and_notify
from config import CRON_INTERVAL

logger = logging.getLogger(__name__)


def init_crons() -> List[Any]:
    return [
        cron(fetch_data_and_notify, CRON_INTERVAL.fetch_data_and_notify),
    ]


async def cron(callback: Callable[[], Any], interval: int) -> None:
    while True:
        try:
            await callback()
        except Exception as e:
            logger.error("Error executing cron : {} with exception : {}\n{}"
                         "".format(callback.__name__, e, traceback.format_exc()))
        finally:
            await asyncio.sleep(interval)
